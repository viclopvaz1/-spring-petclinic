package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DonacionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DonacionControllerTests {

	@Autowired
	private DonacionController donacionController;

	@MockBean
	private DonacionService donacionService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private CausaService causaService;

	@MockBean
	private VetService vetService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private AdiestradorService adiestradorService;

	@MockBean
	private AuthoritiesService authoritiesService;

	private static final int TEST_CAUSA_ID = 1;
	
	private Causa causa;
	
	private Donacion donacion;
	
	private User user;

	@BeforeEach
	void setup() {
		this.causa = new Causa();

		this.user = new User();
		this.user.setUsername("Mary");
		this.user.setPassword("12345");
		this.user.setEnabled(true);

		this.donacion = new Donacion();
		this.donacion.setId(1);
		this.donacion.setCantidad(120);
		this.donacion.setCausa(this.causa);
		this.donacion.setUser(this.user);

		List<Donacion> donaciones = new ArrayList<Donacion>();
		LocalDate fechaFin = LocalDate.parse("2020-05-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate fechaInicio = LocalDate.parse("2020-01-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		donaciones.add(this.donacion);
		this.causa.setId(TEST_CAUSA_ID);
		this.causa.setDineroRecaudado(120);
		this.causa.setDonaciones(donaciones);
		this.causa.setFechaFin(fechaFin);
		this.causa.setFechaInicio(fechaInicio);
		this.causa.setObjetivo(1200);
		this.causa.setOng("ONG");
		this.causa.setValido(true);


		BDDMockito.given(this.causaService.findCausaById(TEST_CAUSA_ID)).willReturn(this.causa);

	}

	@WithMockUser(value = "spring")
	@Test
	void testNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("donaciones/createOrUpdateDonacion"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotExistNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", 151)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


}
