
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
		"1200, 500, ong, 2020-05-06, 2020-05-10, true", "2000, 20, Mi mascota, 2020-04-01, 2020-03-05, false", "2000, 2000, Mi mascota, 2020-04-01, 2020-03-05, false"
	})
	public void addNewCausaWithCsvSourceExitoso(final Integer objetivo, final Integer dineroRecaudado, final String ong, final LocalDate fechaInicio, final LocalDate fechaFin, final boolean valido) {
		Causa causa = new Causa();
		causa.setObjetivo(objetivo);
		causa.setDineroRecaudado(dineroRecaudado);
		causa.setFechaFin(fechaFin);
		causa.setFechaInicio(fechaInicio);
		causa.setOng(ong);
		causa.setValido(valido);
		this.causaService.saveCausa(causa);
		org.assertj.core.api.Assertions.assertThat(causa.getId()).isNotNull();
	}

	@ParameterizedTest
	@CsvSource({
		" , 500, ong, 2020-05-06, 2020-05-20, true", " 1200,, ong, 2020-05-06, 2020-05-20, true", "1200, 0,, 2020-05-06, 2020-05-20, true", "1200,0, ong,, 2020-05-20, true", "1200,0, ong, 2020-05-06,, true"
	})
	public void addNewCausaWithCsvSourceNoExitoso(final Integer objetivo, final Integer dineroRecaudado, final String ong, final LocalDate fechaInicio, final LocalDate fechaFin, final boolean valido) {
		//Prueba introducir un valor null distinto en cada causa, se prueban todos menos el atributo valido, porque al ser boolean no puede ser null
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			Causa causa = new Causa();
			causa.setObjetivo(objetivo);
			causa.setDineroRecaudado(dineroRecaudado);
			causa.setFechaFin(fechaFin);
			causa.setFechaInicio(fechaInicio);
			causa.setOng(ong);
			causa.setValido(valido);
			this.causaService.saveCausa(causa);
		});
	}

	@ParameterizedTest
	@CsvSource({
		" 1,, 500, ong, 2020-05-06, 2020-05-20, true", " 2,1200,, ong, 2020-05-06, 2020-05-20, true", "3,1200, 0,, 2020-05-06, 2020-05-20, true", "1,1200,0, ong,, 2020-05-20, true", "2,1200,0, ong, 2020-05-06,, true"
	})
	public void updateCausaWithCsvSourceNoExitoso(final Integer id, final Integer nuevoObjetivo, final Integer nuevoDineroRecaudado, final String nuevoOng, final LocalDate nuevoFechaInicio, final LocalDate nuevoFechaFin, final boolean nuevoValido) {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			Causa causa = this.causaService.findCausaById(id);
			causa.setObjetivo(nuevoObjetivo);
			causa.setDineroRecaudado(nuevoDineroRecaudado);
			causa.setFechaFin(nuevoFechaFin);
			causa.setFechaInicio(nuevoFechaInicio);
			causa.setOng(nuevoOng);
			causa.setValido(nuevoValido);
			this.causaService.saveCausa(causa);
			causa = this.causaService.findCausaById(id);
		});
	}

	@ParameterizedTest
	@CsvSource({
		"1 ,3000, 500, ong, 2020-05-06, 2020-05-10, false", "2 ,2000, 20, Mi mascota 2, 2020-04-01, 2020-03-05, false", "2 ,2000, 20, Mi mascota 2, 2020-09-01, 2020-10-03, true"
	})
	public void updateCausaWithCsvSourceExitoso(final Integer id, final Integer nuevoObjetivo, final Integer nuevoDineroRecaudado, final String nuevoOng, final LocalDate nuevoFechaInicio, final LocalDate nuevoFechaFin, final boolean nuevoValido) {
		Causa causa = this.causaService.findCausaById(id);
		causa.setObjetivo(nuevoObjetivo);
		causa.setDineroRecaudado(nuevoDineroRecaudado);
		causa.setFechaFin(nuevoFechaFin);
		causa.setFechaInicio(nuevoFechaInicio);
		causa.setOng(nuevoOng);
		causa.setValido(nuevoValido);
		this.causaService.saveCausa(causa);

		causa = this.causaService.findCausaById(id);
		org.assertj.core.api.Assertions.assertThat(causa.getDineroRecaudado()).isEqualTo(nuevoDineroRecaudado);
		org.assertj.core.api.Assertions.assertThat(causa.getObjetivo()).isEqualTo(nuevoObjetivo);
		org.assertj.core.api.Assertions.assertThat(causa.getOng()).isEqualTo(nuevoOng);
		org.assertj.core.api.Assertions.assertThat(causa.getFechaInicio()).isEqualTo(nuevoFechaInicio);
		org.assertj.core.api.Assertions.assertThat(causa.getFechaFin()).isEqualTo(nuevoFechaFin);
		org.assertj.core.api.Assertions.assertThat(causa.isValido()).isEqualTo(nuevoValido);
	}
	
	@Test//Negativo es null la causa
	public void saveFailCausa() {
		Causa causa = null;		
		org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
			this.causaService.saveCausa(causa);
		});
	}

	@ParameterizedTest
	@CsvSource({
		"1", "2", "3"
	})
	public void deleteCausaWithCsvSourceExitoso(final Integer id) {
		Causa causa = this.causaService.findCausaById(id);
		org.assertj.core.api.Assertions.assertThat(causa).isNotNull();
		this.causaService.deleteCausa(causa);

		Causa causaBorrada = this.causaService.findCausaById(id);
		org.assertj.core.api.Assertions.assertThat(causaBorrada).isNull();

	}
	@ParameterizedTest
	@CsvSource({
		"6", "5", "4"
	})
	public void deleteCausaWithCsvSourceNoExitoso(final Integer id) {
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			Causa causa = this.causaService.findCausaById(id);
			this.causaService.deleteCausa(causa);
		});
	}

	@Test
	public void testCountWithInitialData() {
		int count = this.causaService.causaCount();
		org.assertj.core.api.Assertions.assertThat(count).isEqualTo(3);
	}

	@Test
	public void testFindAllValidoWithInitialData() {
		Collection<Causa> causas = (Collection<Causa>) this.causaService.findAll();
		org.assertj.core.api.Assertions.assertThat(causas).hasSize(2);
	}

	@ParameterizedTest
	@CsvSource({
		"1", "2", "3"
	})
	public void testFindCausaByIdWithCsvSourceExitoso(final Integer id) {
		Causa causas = this.causaService.findCausaById(id);
		org.assertj.core.api.Assertions.assertThat(causas).isNotNull();
	}

	@ParameterizedTest
	@CsvSource({
		"4", "5", "6"
	})
	public void testFindCausaByIdWithCsvSourceNoExitoso(final Integer id) {
		Assertions.assertThrows(AssertionError.class, () -> {
			Causa causas = this.causaService.findCausaById(id);
			org.assertj.core.api.Assertions.assertThat(causas).isNotNull();
		});
	}

	@ParameterizedTest
	@CsvSource({
		"owner1", "vet1"
	})
	public void testFindCausaByUsernameWithCsvSourceExitoso(final String username) {
		Collection<Causa> causas = this.causaService.findCausaByUsername(username);
		org.assertj.core.api.Assertions.assertThat(causas).isNotEmpty();
	}

	@ParameterizedTest
	@CsvSource({
		"0wner1", "v3t1", "v3t2", "v3t3", "v3t4",
	})
	public void testFindCausaByUsernameWithCsvSourceNoExitoso(final String username) {
		Assertions.assertThrows(AssertionError.class, () -> {
			Collection<Causa> causas = this.causaService.findCausaByUsername(username);
			org.assertj.core.api.Assertions.assertThat(causas).isNotEmpty();
		});
	}

	@Test
	public void testFindCausaByValidoWithInitialData() {
		Collection<Causa> causas = this.causaService.findCausaByValido();
		org.assertj.core.api.Assertions.assertThat(causas).hasSize(1);
	}

}
