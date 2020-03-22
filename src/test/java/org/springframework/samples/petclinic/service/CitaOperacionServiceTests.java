package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CitaOperacionServiceTests {
	
	@Autowired
	protected CitaOperacionService citaOperacionService;
	
	@Autowired
	protected VetService vetService;
	
	@Test
	void shouldFindCitaOperacionWithCorrectId() {
		CitaOperacion citaOperacion2 = this.citaOperacionService.findCitaOperacionById(2);
		assertThat(citaOperacion2.getVet().getFirstName().equals("Helen"));
		assertThat(citaOperacion2.getPrecio().equals(50.0));
	}
	
	@Test
	void shouldFindCitaOperacionWithCorrectId2() {
		CitaOperacion citaOperacion2 = this.citaOperacionService.findCitaOperacionById(2);
		assertThat(citaOperacion2.getPet().getName().equals("Jewel"));
		assertThat(citaOperacion2.getCantidadPersonal().equals(3));
	}
	
	@Test
	void shouldFindCitaOperacionByOwnerId() {
		Collection<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByTipoOperacion("Cirugia Visual");
		assertThat(citasOperaciones.size()).isEqualTo(0);
		
		citasOperaciones = this.citaOperacionService.findCitaOperacionByTipoOperacion("Cirugia basica");
		assertThat(citasOperaciones.size()).isEqualTo(1);
	}
	
	@Test
	void findFindAllCitaOperacionWithInitialData() {
		Collection<CitaOperacion> citaOperacion = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		assertThat(citaOperacion.size() == 4);
	}

}
