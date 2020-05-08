package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DonacionServiceTests {

	@Autowired
	private DonacionService donacionService;
	

	@ParameterizedTest //Positivo
	@Order(6)
	@CsvSource({"100", "1200"})
	public void saveDonacion(final Integer cantidad) {
		Donacion donacion = new Donacion();
		donacion.setCantidad(cantidad);
		this.donacionService.saveDonacion(donacion);
		Assertions.assertThat(donacion.getId() != null);		

	}
	
	@ParameterizedTest//Negativo
	@Order(7)
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
	@Order(8)
	public void saveFailDonacionNull() {
		Donacion donacion = new Donacion();	
		donacion.setCantidad(null);		
		org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.donacionService.saveDonacion(donacion);
		});
	}
	
	@ParameterizedTest // POSITIVO
	@Order(1)
	@CsvSource({
		"3", "4"
	})
	public void testFindDonacionByIdWithCsvSource(final Integer id) {
		Donacion nuevo = new Donacion();
		nuevo.setCantidad(10);
		nuevo.setId(id);
		this.donacionService.saveDonacion(nuevo);
		Donacion donacion = this.donacionService.findById(id);
		Assertions.assertThat(donacion).isNotNull();
	}
	
	@ParameterizedTest // NEGATIVO no encuentra ID
	@Order(2)
	@CsvSource({
		"10", "40"
	})
	public void testFindNotDonacionByIdWithCsvSource(final Integer id) {
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.donacionService.findById(id);
		});
	}
	
	

	@Test
	@Order(3)
	public void testCountWithInitialData() {
		int count = this.donacionService.donacionCount();
		assertEquals(count, 2);
	}

	@Test
	@Order(4)
	public void testFindById() {
		Donacion nuevo = new Donacion();
		nuevo.setCantidad(10);
		nuevo.setId(5);
		this.donacionService.saveDonacion(nuevo);
		Donacion donacion = this.donacionService.findById(5);
		Assertions.assertThat(donacion != null);
	}

}
