/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following
 * services provided by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary
 * set up time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning
 * that we don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable,
 * which uses autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is
 * executed in its own transaction, which is automatically rolled back by
 * default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script.
 * <li>An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean
 * lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CitaAdiestramientoServiceTests {

	@Autowired
	protected CitaAdiestramientoService citaAdiestramientoService;

	@Autowired
	protected AdiestradorService adiestradorService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected TipoAdiestramientoService tipoAdiestramientoService;
	
	@ParameterizedTest
	@CsvSource({
		"1", "2", "3"
	})
	
	void shouldFindCitaAdiestramientos(final Integer id) {
	CitaAdiestramiento citas = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
	assertThat(citas).isNotNull();
	
	
}
	@Test
	void shouldFindCitaAdiestramientos() {
		Collection<CitaAdiestramiento> CitaAdiestramientos = (Collection<CitaAdiestramiento>) this.citaAdiestramientoService
				.findAll();
		CitaAdiestramiento CitaAdiestramiento = EntityUtils.getById(CitaAdiestramientos, CitaAdiestramiento.class, 1);
		assertThat(CitaAdiestramiento.getAdiestrador().getFirstName()).isEqualTo("Alberto");
		assertThat(CitaAdiestramiento.getPrecio()).isEqualTo(50.0);
	}

	@Test
	void shouldFindCitaAdiestramientos2() {
		Collection<CitaAdiestramiento> CitaAdiestramientos = (Collection<CitaAdiestramiento>) this.citaAdiestramientoService
				.findAll();
		CitaAdiestramiento CitaAdiestramiento = EntityUtils.getById(CitaAdiestramientos, CitaAdiestramiento.class, 1);
		assertThat(CitaAdiestramiento.getDuracion()).isEqualTo(30);
		assertThat(CitaAdiestramiento.getPet().getId()).isEqualTo(1);
	}
		
	@Test
	void shouldFindCitaAdiestramientoByPet() {
		Collection<CitaAdiestramiento> CitaAdiestramientos = this.citaAdiestramientoService
				.findCitaAdiestramientoByPet("cat");
		assertThat(CitaAdiestramientos.size()).isEqualTo(2);

	}
	@ParameterizedTest
	@CsvSource({
		"snake", "bird", "lizard"
	})
	void shouldFindCitaAdiestramientoByPetNegative(final String type) {
		Collection<CitaAdiestramiento> CitaAdiestramientos = this.citaAdiestramientoService
				.findCitaAdiestramientoByPet(type);
		assertThat(CitaAdiestramientos.isEmpty()).isTrue();

		// CitaAdiestramientos =
		// this.CitaAdiestramientoService.findCitaAdiestramientoByPet("dog");
//		CitaAdiestramiento CitaAdiestramiento = EntityUtils.getById(CitaAdiestramientos, CitaAdiestramiento.class, 1);
//		assertThat(CitaAdiestramiento.getPet().getType().getName()).isEqualTo("dog");
	}

	@Test
	void shouldFindCitaAdiestramientoByOwnerId() {
		Collection<CitaAdiestramiento> CitaAdiestramientos = this.citaAdiestramientoService
				.findCitaAdiestramientoByOwnerId(2);
		assertThat(CitaAdiestramientos.size()).isEqualTo(1);
	}

	@Test
	void shouldFindCitaAdiestramientoByOwnerIdNegative() {
		Collection<CitaAdiestramiento> CitaAdiestramientos = this.citaAdiestramientoService
				.findCitaAdiestramientoByOwnerId(4);
		assertThat(CitaAdiestramientos.isEmpty()).isTrue();

	}

	@Test
	void countCitaAdiestramientoByOwnerId() {
		int CitaAdiestramientos = this.citaAdiestramientoService.citaAdiestramientoCount();
		assertThat(CitaAdiestramientos).isEqualTo(5);

	}


	@ParameterizedTest
	@CsvSource({
		"1", "2", "3"
	})
	public void deleteCitaAdiestramientoWithCsvSourceSuccessful(final Integer id) {
		CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento).isNotNull();
		this.citaAdiestramientoService.deleteCitaAdiestramiento(citaAdiestramiento);
		CitaAdiestramiento citaAdiestramientoBorrada = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramientoBorrada).isNull();

	}
	@ParameterizedTest
	@CsvSource({
		"8", "7", "6"
	})
	public void deleteCitaAdiestramientoWithCsvSourceFail(final Integer id) {
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
			this.citaAdiestramientoService.deleteCitaAdiestramiento(citaAdiestramiento);
		});
	}

	@ParameterizedTest
	@CsvSource({
		"1,1, 2020-03-25, 15:00, 30, 100.0, false, 1, Adiestramiento ppp, 1"
	})
	public void addNewCitaAdiestramientoWithCsvSourceSuccessful(final int id, final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int adiestradorId, final String tipoAdiestramientoName, final int ownerId) {
		CitaAdiestramiento citaAdiestramiento = new CitaAdiestramiento();
		citaAdiestramiento.setPet(this.petService.findPetById(petId));
		citaAdiestramiento.setFechaInicio(fechaInicio);
		citaAdiestramiento.setHora(hora);
		citaAdiestramiento.setDuracion(duracion);
		citaAdiestramiento.setPrecio(precio);
		citaAdiestramiento.setPagado(pagado);
		citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorById(adiestradorId));
		citaAdiestramiento.setTipoAdiestramiento(this.tipoAdiestramientoService.findTipoAdiestramientoByName(tipoAdiestramientoName));
		citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
		
		this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getId()).isNotNull();
	}

	@ParameterizedTest
	@CsvSource({
		"1,1, , 15:00, 30, 100.0, false, 1, Adiestramiento ppp, 1"
	})
	public void addNewCitaAdiestramientoWithCsvSourceFail(final int id, final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int adiestradorId, final String tipoAdiestramientoName, final int ownerId) {
		//Prueba introducir un valor null distinto en cada citaAdiestramiento, se prueban todos menos el atributo valido, porque al ser boolean no puede ser null
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			CitaAdiestramiento citaAdiestramiento = new CitaAdiestramiento();
			citaAdiestramiento.setPet(this.petService.findPetById(petId));
			citaAdiestramiento.setFechaInicio(fechaInicio);
			citaAdiestramiento.setHora(hora);
			citaAdiestramiento.setDuracion(duracion);
			citaAdiestramiento.setPrecio(precio);
			citaAdiestramiento.setPagado(pagado);
			citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorById(adiestradorId));
			citaAdiestramiento.setTipoAdiestramiento(this.tipoAdiestramientoService.findTipoAdiestramientoByName(tipoAdiestramientoName));
			citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
			this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);
		});
	}

	@ParameterizedTest
	@CsvSource({
		"1,1, , 15:00, 30, 100.0, false, 1, Adiestramiento ppp, 1"
	})
	public void updateCitaAdiestramientoWithCsvSourceFail(final int id,final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int adiestradorId, final String tipoAdiestramientoName, final int ownerId) {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
			citaAdiestramiento.setPet(this.petService.findPetById(petId));
			citaAdiestramiento.setFechaInicio(fechaInicio);
			citaAdiestramiento.setHora(hora);
			citaAdiestramiento.setDuracion(duracion);
			citaAdiestramiento.setPrecio(precio);
			citaAdiestramiento.setPagado(pagado);
			citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorById(adiestradorId));
			citaAdiestramiento.setTipoAdiestramiento(this.tipoAdiestramientoService.findTipoAdiestramientoByName(tipoAdiestramientoName));
			citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
			this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);
			citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
		});
	}

	@ParameterizedTest
	@CsvSource({
		"1,1, 2020-03-25, 15:00, 30, 100.0, false, 1, Adiestramiento ppp, 1"
	})
	public void updateCitaAdiestramientoWithCsvSourceSuccessful(final int id,final int petId, final LocalDate fechaInicio, final LocalTime hora, final Integer duracion,
			final Double precio, final boolean pagado, final int adiestradorId, final String tipoAdiestramientoName, final int ownerId) {
		
		CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
		citaAdiestramiento.setPet(this.petService.findPetById(petId));
		citaAdiestramiento.setFechaInicio(fechaInicio);
		citaAdiestramiento.setHora(hora);
		citaAdiestramiento.setDuracion(duracion);
		citaAdiestramiento.setPrecio(precio);
		citaAdiestramiento.setPagado(pagado);
		citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorById(adiestradorId));
		citaAdiestramiento.setTipoAdiestramiento(this.tipoAdiestramientoService.findTipoAdiestramientoByName(tipoAdiestramientoName));
		citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
		this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);

		citaAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoById(id);
		
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getDuracion()).isEqualTo(duracion);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getFechaInicio()).isEqualTo(fechaInicio);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getHora()).isEqualTo(hora);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getPrecio()).isEqualTo(precio);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getAdiestrador().getId()).isEqualTo(adiestradorId);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.isPagado()).isEqualTo(pagado);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getOwner().getId()).isEqualTo(ownerId);
		org.assertj.core.api.Assertions.assertThat(citaAdiestramiento.getTipoAdiestramiento().getName()).isEqualTo(tipoAdiestramientoName);



	}






}
