
package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/", "/oups").permitAll().antMatchers("/users/new").permitAll()
				.antMatchers("/citasOperaciones").permitAll()
				.antMatchers("/citasOperaciones/**").hasAnyAuthority("veterinarian", "admin")
				.antMatchers("/citaOperacion/{id}").hasAnyAuthority("veterinarian", "admin")
				.antMatchers("/citaOperacion/{citaOperacionId}/edit/{citaOperacionId}").hasAnyAuthority("veterinarian", "admin")
				.antMatchers("/citaOperacion/{citaOperacionId}/delete").hasAnyAuthority("veterinarian", "admin")
				.antMatchers(HttpMethod.GET, "/citaOperacion/new").hasAnyAuthority("veterinarian", "admin")
				.antMatchers(HttpMethod.POST, "/citaOperacion/new/{petId}").hasAnyAuthority("veterinarian", "admin")
				.antMatchers(HttpMethod.GET, "/citasAdiestramiento/new/{ownerId}/{petId}").hasAnyAuthority("adiestrador", "admin")
				.antMatchers(HttpMethod.POST, "/citasAdiestramiento/new/{ownerId}/{petId}").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/citaAdiestramiento/{id}").hasAnyAuthority("veterinarian", "admin","adiestrador")
				.antMatchers(HttpMethod.GET,"/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}").hasAnyAuthority("adiestrador", "admin")
				.antMatchers(HttpMethod.POST,"/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/citaAdiestramiento/{citaAdiestramientoId}/delete").hasAnyAuthority("adiestrador", "admin")			
				.antMatchers("/citasAdiestramiento").hasAnyAuthority("adiestrador", "admin")
//				.antMatchers("/citasAdiestramiento/**").hasAnyAuthority("owner", "admin")
				.antMatchers("/citasAdiestramiento/{ownerId}").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/citasAdiestramiento/new").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/citasAdiestramiento/find").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/citasAdiestramiento/all").hasAnyAuthority("adiestrador", "admin")
				.antMatchers("/adiestradores").permitAll()
				.antMatchers("/adiestradores/5").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/owners/**").hasAnyAuthority("owner", "admin", "veterinarian","adiestrador")
//				.antMatchers("/owners/find").hasAnyAuthority("owner", "admin")
//				.antMatchers("/owners").hasAnyAuthority("owner", "admin", "veterinarian")
//				.antMatchers("/owners/{ownerId}").hasAnyAuthority("owner", "admin", "veterinarian")
				.antMatchers("/citasAdiestramiento/**").hasAnyAuthority("owner", "admin")
				.antMatchers("/vets/**").authenticated().antMatchers("/donacion/**").hasAnyAuthority("owner", "admin")
				.antMatchers("/users/addmoney").permitAll()
				.antMatchers(HttpMethod.GET, "/causa/new").hasAnyAuthority("veterinarian", "owner", "admin")
				.antMatchers("/causa").permitAll()
				.antMatchers("/causa/propias").hasAnyAuthority("veterinarian", "owner", "admin")
				.antMatchers("/causa/{causaId}/edit").hasAnyAuthority("veterinarian")
				.antMatchers("/causa/{causaId}/delete").hasAnyAuthority("veterinarian")
				.antMatchers("/causa/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/causa/new").hasAnyAuthority("veterinarian", "owner", "admin")
				.antMatchers("/causa/noValidas").hasAnyAuthority("veterinarian")
				.antMatchers("/donacion/**").hasAnyAuthority("owner", "admin","veterinarian", "adiestrador")
				.anyRequest().denyAll().and().formLogin()
				/* .loginPage("/login") */
				.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
				.usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
				.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?")
				.passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return encoder;
	}

}
