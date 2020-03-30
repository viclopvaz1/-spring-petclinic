package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AdiestradorRepository;
import org.springframework.samples.petclinic.repository.DonacionRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DonacionServiceTests {

	@Autowired
	private DonacionService donacionService;
	
	@Autowired
	private CausaService causaService;

	@ParameterizedTest //Positivo
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
	
	@ParameterizedTest//Negativo
	@CsvSource({"-100"})
	public void saveFailDonacionNegativa(final Integer cantidad) {
		Donacion donacion = new Donacion();
		donacion.setCantidad(cantidad);
		org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.donacionService.saveDonacion(donacion);
		});
	}
	
	

//no pase el objetivo
	
	
	@Test//Negativo es null la donacion
	public void saveFailDonacionNull() {
		Donacion donacion = new Donacion();	
		donacion.setCantidad(null);		
		org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.donacionService.saveDonacion(donacion);
		});
	}
	
	@ParameterizedTest // POSITIVO
	@CsvSource({
		"1", "2"
	})
	public void testFindDonacionByIdWithCsvSource(final Integer id) {
		Donacion donacion = this.donacionService.findById(id);
		Assertions.assertThat(donacion).isNotNull();
	}
	
	@ParameterizedTest // NEGATIVO no encuentra ID
	@CsvSource({
		"10", "40"
	})
	public void testFindNotDonacionByIdWithCsvSource(final Integer id) {
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.donacionService.findById(id);
		});
	}
	
	

	@Test
	public void testCountWithInitialData() {
		int count = this.donacionService.donacionCount();
		assertEquals(count, 2);
	}

	@Test
	public void testFindById() {
		Donacion donacion = this.donacionService.findById(1);
		Assertions.assertThat(donacion != null);
	}

}
