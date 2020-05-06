package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaOperacionRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CitaOperacionServiceTests {
	
	@Autowired
	protected CitaOperacionService citaOperacionService;
	
	@Autowired
	protected VetService vetService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected TipoOperacionService tipoOperacionService;
	
	
	@ParameterizedTest
	@Order(10)
	@CsvSource({
		"1, 2020-03-25, 15:00, 30, 100.0, false, 1, Cirugia visual, 2.0"
	})
	public void addNewCitaOperacion(final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int vetId, final String tipoOperacionName, final Double cantidadPersonal) {
		CitaOperacion citaOperacion = new CitaOperacion();
		Collection<CitaOperacion> found = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		citaOperacion.setPet(this.petService.findPetById(petId));
		citaOperacion.setFechaInicio(fechaInicio);
		citaOperacion.setHora(hora);
		citaOperacion.setDuracion(duracion);
		citaOperacion.setPrecio(precio);
		citaOperacion.setPagado(pagado);
		citaOperacion.setVet(this.vetService.findVetById(vetId));
		citaOperacion.setTipoOperacion(this.tipoOperacionService.findTipoOperacionByName(tipoOperacionName));
		citaOperacion.setCantidadPersonal(cantidadPersonal);
		this.citaOperacionService.saveCitaOperacion(citaOperacion);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getId() != null);
		
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		org.assertj.core.api.Assertions.assertThat(citasOperaciones.size()).isEqualTo(found.size() + 1);
	}
	
	@ParameterizedTest
	@Order(11)
	@CsvSource({
		"1, , 15:00, 30, 100.0, false, 1, Cirugia visual, 2.0"
	})
	public void addFailNewCitaOperacion(final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int vetId, final String tipoOperacionName, final Double cantidadPersonal) {
		CitaOperacion citaOperacion = new CitaOperacion();
		citaOperacion.setPet(this.petService.findPetById(petId));
		citaOperacion.setFechaInicio(fechaInicio);
		citaOperacion.setHora(hora);
		citaOperacion.setDuracion(duracion);
		citaOperacion.setPrecio(precio);
		citaOperacion.setPagado(pagado);
		citaOperacion.setVet(this.vetService.findVetById(vetId));
		citaOperacion.setTipoOperacion(this.tipoOperacionService.findTipoOperacionByName(tipoOperacionName));
		citaOperacion.setCantidadPersonal(cantidadPersonal);
		
		
		Assertions.assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.citaOperacionService.saveCitaOperacion(citaOperacion);
		});
	}
	
	@ParameterizedTest
	@Order(12)
	@CsvSource({
		"1, 2020-03-25, 15:00, 50, 100.0, false, Cirugia visual, 5.0"
	})
	public void updateCitaOperacion(final int citaOperacionId, final LocalDate nuevaFechaInicio, final LocalTime nuevaHora, final Integer nuevaDuracion,
			final Double nuevoPrecio, final boolean nuevoPagado, final String nuevoTipoOperacionName, final Double nuevaCantidadPersonal) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId).get();
		citaOperacion.setFechaInicio(nuevaFechaInicio);
		citaOperacion.setHora(nuevaHora);
		citaOperacion.setDuracion(nuevaDuracion);
		citaOperacion.setPrecio(nuevoPrecio);
		citaOperacion.setPagado(nuevoPagado);
		citaOperacion.setTipoOperacion(this.tipoOperacionService.findTipoOperacionByName(nuevoTipoOperacionName));
		citaOperacion.setCantidadPersonal(nuevaCantidadPersonal);
		this.citaOperacionService.saveCitaOperacion(citaOperacion);
		
		citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId).get();
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getFechaInicio()).isEqualTo(nuevaFechaInicio);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getHora()).isEqualTo(nuevaHora);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getDuracion()).isEqualTo(nuevaDuracion);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getPrecio()).isEqualTo(nuevoPrecio);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.isPagado()).isEqualTo(nuevoPagado);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getTipoOperacion().getName()).isEqualTo(nuevoTipoOperacionName);
		org.assertj.core.api.Assertions.assertThat(citaOperacion.getCantidadPersonal()).isEqualTo(nuevaCantidadPersonal);

	}
	
	@Test
	@Order(1)
	void shouldFindAllCitasOperaciones() {
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		org.assertj.core.api.Assertions.assertThat(citasOperaciones).hasSize(5);
	}
	
	@Test
	@Order(2)
	void shouldNotFindAll() {
		SpringDataCitaOperacionRepository springDataCitaOperacionRepository = Mockito.mock(SpringDataCitaOperacionRepository.class);
		this.citaOperacionService = new CitaOperacionService(springDataCitaOperacionRepository);
		Mockito.when(springDataCitaOperacionRepository.findAll()).thenReturn(new HashSet<CitaOperacion>());
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.citaOperacionService.findAll();
		});
	}
	
	@Test
	@Order(3)
	void shouldFindCitaOperacionWithCorrectId() {
		CitaOperacion citaOperacion2 = this.citaOperacionService.findCitaOperacionById(2).get();
		org.assertj.core.api.Assertions.assertThat(citaOperacion2.getVet().getFirstName().equals("Helen"));
		org.assertj.core.api.Assertions.assertThat(citaOperacion2.getPrecio().equals(50.0));
	}
	
	@Test
	@Order(4)
	void shouldFindCitaOperacionWithIncorrectId() {
		CitaOperacion citaOperacion2 = this.citaOperacionService.findCitaOperacionById(2).get();
		org.assertj.core.api.Assertions.assertThat(citaOperacion2.getVet().getFirstName().equals("Helen"));
		org.assertj.core.api.Assertions.assertThat(citaOperacion2.getPrecio().equals(50.0));
	}
	
	@Test
	@Order(5)
	void shouldFindCitaOperacionWithCorrectPetId() {
		Collection<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByPet(1);
		org.assertj.core.api.Assertions.assertThat(citasOperaciones.size()).isEqualTo(1);
	}
	
	@Test
	@Order(6)
	void shouldFindCitaOperacionWithIncorrectPetId() {
		Collection<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByPet(10);
		org.assertj.core.api.Assertions.assertThat(citasOperaciones.size()).isEqualTo(0);
	}
	
	@Test
	@Order(7)
	void shouldFindCitaOperacionByTipoOperacion() {
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findCitaOperacionByTipoOperacion("Cirugia basica");
		org.assertj.core.api.Assertions.assertThat(citasOperaciones.size()).isEqualTo(1);
	}
	
	@Test
	@Order(8)
	void shouldFindCitaOperacionWithTipoOperacionWithoutCitas() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.citaOperacionService.findCitaOperacionByTipoOperacion("hola");
		});
	}
	
	@Test
	@Order(9)
	void shouldFindCitaOperacionWithIncorrectTipoOperacion() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.citaOperacionService.findCitaOperacionByTipoOperacion("hola");
		});
	}
	
}
