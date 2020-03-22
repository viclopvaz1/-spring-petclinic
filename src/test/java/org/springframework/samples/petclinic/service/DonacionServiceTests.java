package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DonacionServiceTests {

	@Autowired
	private DonacionService donacionService;

	@ParameterizedTest
	@CsvSource({"100", "1200"})
	public void saveDonacion(final Integer cantidad) {
		Donacion donacion = new Donacion();
		donacion.setCantidad(cantidad);
		this.donacionService.saveDonacion(donacion);
		Assertions.assertThat(donacion.getId() != null);

//			User usuario =new User();
//	        user.setUsername("Sam");
//	        user.setPassword("supersecretpassword");
//	        user.setEnabled(true);  	             
//			Causa causa = new Causa();
//			causa.setObjetivo(1000);
//			causa.setDineroRecaudado(21);
//			causa.setFechaFin(LocalDate.now());
//			causa.setFechaInicio(LocalDate.now());
//			causa.setOng("ajjaj");
//			causa.setValido(false);			

	}

	@Test
	public void testCountWithInitialData() {
		int count = this.donacionService.donacionCount();
		assertEquals(count, 1);
	}

	@Test
	public void testFindById() {
		Donacion donacion = this.donacionService.findById(1);
		Assertions.assertThat(donacion != null);
	}

}
