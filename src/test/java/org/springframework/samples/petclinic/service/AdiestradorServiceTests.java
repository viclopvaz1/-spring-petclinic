package org.springframework.samples.petclinic.service;



import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@DisplayName("A stack")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AdiestradorServiceTests {
	
	@Autowired
	protected AdiestradorService		adiestradorService;
	
	Adiestrador			adiestrador;
	
	@Test
	void PositiveFindAdiestrador() {
		Iterable<Adiestrador> adiestrador = this.adiestradorService.findAll();
		Assertions.assertThat(adiestrador).isNotNull();
	}
	

	@Test
	void shouldNotFindAdiestrador() {
		SpringDataAdiestradorRepository SpringAdiestradorRepository = Mockito.mock(SpringDataAdiestradorRepository.class);
		this.adiestradorService = new AdiestradorService(SpringAdiestradorRepository);
		Mockito.when(SpringAdiestradorRepository.findAll()).thenReturn(new ArrayList<Adiestrador>());
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.adiestradorService.findAll();
		});
	}
	
	@Test
	void shouldNotFindAdiestradorEstrellas() {
		SpringDataAdiestradorRepository SpringAdiestradorRepository = Mockito.mock(SpringDataAdiestradorRepository.class);
		this.adiestradorService = new AdiestradorService(SpringAdiestradorRepository);
		Mockito.when(SpringAdiestradorRepository.findAdiestradorByEstrellas(5)).thenReturn(new ArrayList<Adiestrador>());
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.adiestradorService.findAdiestradorByEstrellas(5);
		});
	}
	
	@Test
	public void checkFindByStar(){
		Collection<Adiestrador> adiestrador = this.adiestradorService.findAdiestradorByEstrellas(5);
		Assertions.assertThat(adiestrador != null);
	}
	
	@Test
	public void checkFindAll(){
		Iterable<Adiestrador> adiestrador = this.adiestradorService.findAll();
		Assertions.assertThat(adiestrador != null);
	}

	@Test
	public void checkCount(){
		Integer adiestrador = this.adiestradorService.adiestradorCount();
		Assertions.assertThat(adiestrador != 0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"adiestrador1", "adiestrador2"
	})
	public void testFindAdiestradorByUsernamesvSource(final String username) {
		Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
		Assertions.assertThat(adiestrador).isNotNull();
	}
	
	@ParameterizedTest
	@CsvSource({ 
		"adiestrador1444", "adiestrador144"
	})
	public void testNotFindAdiestradorByUsermeCsvSource(final String username) {
		Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
		Assertions.assertThat(adiestrador).isNull();
	}
	

}
