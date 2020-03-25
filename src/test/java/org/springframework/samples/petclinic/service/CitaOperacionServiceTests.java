package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
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
	@CsvSource({
		"1, 2020-03-25, 15:00, 30, 100.0, false, 1, Cirugia Visual, 2.0"
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
		Assertions.assertThat(citaOperacion.getId() != null);
		
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		Assertions.assertThat(citasOperaciones.size()).isEqualTo(found.size() + 1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1, 2020-03-25, 15:00, 50, 100.0, false, Cirugia Visual, 5.0"
	})
	public void updateCitaOperacion(final int citaOperacionId, final LocalDate nuevaFechaInicio, final LocalTime nuevaHora, final Integer nuevaDuracion,
			final Double nuevoPrecio, final boolean nuevoPagado, final String nuevoTipoOperacionName, final Double nuevaCantidadPersonal) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId);
		citaOperacion.setFechaInicio(nuevaFechaInicio);
		citaOperacion.setHora(nuevaHora);
		citaOperacion.setDuracion(nuevaDuracion);
		citaOperacion.setPrecio(nuevoPrecio);
		citaOperacion.setPagado(nuevoPagado);
		citaOperacion.setTipoOperacion(this.tipoOperacionService.findTipoOperacionByName(nuevoTipoOperacionName));
		citaOperacion.setCantidadPersonal(nuevaCantidadPersonal);
		this.citaOperacionService.saveCitaOperacion(citaOperacion);
		
		citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId);
		Assertions.assertThat(citaOperacion.getFechaInicio()).isEqualTo(nuevaFechaInicio);
		Assertions.assertThat(citaOperacion.getHora()).isEqualTo(nuevaHora);
		Assertions.assertThat(citaOperacion.getDuracion()).isEqualTo(nuevaDuracion);
		Assertions.assertThat(citaOperacion.getPrecio()).isEqualTo(nuevoPrecio);
		Assertions.assertThat(citaOperacion.isPagado()).isEqualTo(nuevoPagado);
		Assertions.assertThat(citaOperacion.getTipoOperacion().getName()).isEqualTo(nuevoTipoOperacionName);
		Assertions.assertThat(citaOperacion.getCantidadPersonal()).isEqualTo(nuevaCantidadPersonal);

	}
	
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
