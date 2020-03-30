
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
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
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCausaRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CausaServiceTests {

	@Autowired
	private CausaService causaService;


	@ParameterizedTest
	@CsvSource({
		"1200, 500, ong, 2020-05-06, 2020-05-10, true", "2000, 20, Mi mascota, 2020-04-01, 2020-03-05, false"
	})
	public void addNewCausa(final Integer objetivo, final Integer dineroRecaudado, final String ong, final LocalDate fechaInicio, final LocalDate fechaFin, final boolean valido) {
		Causa causa = new Causa();
		causa.setObjetivo(objetivo);
		causa.setDineroRecaudado(dineroRecaudado);
		causa.setFechaFin(fechaFin);
		causa.setFechaInicio(fechaInicio);
		causa.setOng(ong);
		causa.setValido(valido);
		this.causaService.saveCausa(causa);
		Assertions.assertThat(causa.getId() != null);
	}

	@ParameterizedTest
	@CsvSource({
		"1 ,3000, 500, ong, 2020-05-06, 2020-05-10, false", "3 ,2000, 20, Mi mascota 2, 2020-04-01, 2020-03-05, false"
	})
	public void updateCausa(final Integer id, final Integer nuevoObjetivo, final Integer nuevoDineroRecaudado, final String nuevoOng, final LocalDate nuevoFechaInicio, final LocalDate nuevoFechaFin, final boolean nuevoValido) {
		Causa causa = this.causaService.findCausaById(id);
		causa.setObjetivo(nuevoObjetivo);
		causa.setDineroRecaudado(nuevoDineroRecaudado);
		causa.setFechaFin(nuevoFechaFin);
		causa.setFechaInicio(nuevoFechaInicio);
		causa.setOng(nuevoOng);
		causa.setValido(nuevoValido);
		this.causaService.saveCausa(causa);

		// retrieving new name from database
		causa = this.causaService.findCausaById(id);
		Assertions.assertThat(causa.getDineroRecaudado()).isEqualTo(nuevoDineroRecaudado);
		Assertions.assertThat(causa.getObjetivo()).isEqualTo(nuevoObjetivo);
		Assertions.assertThat(causa.getOng()).isEqualTo(nuevoOng);
		Assertions.assertThat(causa.getFechaInicio()).isEqualTo(nuevoFechaInicio);
		Assertions.assertThat(causa.getFechaFin()).isEqualTo(nuevoFechaFin);
		Assertions.assertThat(causa.isValido()).isEqualTo(nuevoValido);
	}
	
	@Test//Negativo es null la causa
	public void saveFailCausa() {
		Causa causa = null;		
		org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
			this.causaService.saveCausa(causa);
		});
	}

	@ParameterizedTest
	@CsvSource({//NEGATIVO 
		"1", "3"
	})
	public void deleteCausa(final Integer id) {
		Causa causa = this.causaService.findCausaById(id);
		Assertions.assertThat(causa != null);
		this.causaService.deleteCausa(causa);
		causa =  this.causaService.findCausaById(id);
		Assertions.assertThat(causa).isNull();

	}
	
	@ParameterizedTest
	@CsvSource({
		"1", "3"
	})
	public void testFindCausaByIdWithCsvSource(final Integer id) {
		Causa causas = this.causaService.findCausaById(id);
		Assertions.assertThat(causas).isNotNull();
	}
	
	
	@ParameterizedTest // NEGATIVO no encuentra USERNAME
	@CsvSource({
		"Ajhasfdds", "sdfgsdg"
	})
	public void testFindNotCausaByUsernameWithCsvSource(final String username) {
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.causaService.findCausaByUsername(username);
		});
	}
	
	
	@ParameterizedTest // NEGATIVO no encuentra ID
	@CsvSource({
		"10", "14"
	})
	public void testFindNotCausaByIdWithCsvSource(final Integer id) {
		Causa causa = this.causaService.findCausaById(id);
		Assertions.assertThat(causa).isNull();
	}

	@Test
	public void testCountWithInitialData() {
		int count = this.causaService.causaCount();
		Assertions.assertThat(count == 2);
	}

	@Test
	public void testFindAllWithInitialData() {
		Collection<Causa> causas = (Collection<Causa>) this.causaService.findAll();
		Assertions.assertThat(causas.size() == 2);
	}

	@Test
	public void testFindCausaByIdWithInitialData() {
		Causa causas = this.causaService.findCausaById(1);
		Assertions.assertThat(causas != null);
	}

	@Test
	public void testFindCausaByUsernameWithInitialData() {
		Collection<Causa> causas = this.causaService.findCausaByUsername("vet1");
		Assertions.assertThat(!causas.isEmpty());
	}

	@Test
	public void testFindCausaByValidoWithInitialData() {
		Collection<Causa> causas = this.causaService.findCausaByValido();
		Assertions.assertThat(!causas.isEmpty());
	}

}
