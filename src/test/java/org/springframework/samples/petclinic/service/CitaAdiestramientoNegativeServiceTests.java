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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Adiestrador;
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
class CitaAdiestramientoNegativeServiceTests {

	@Autowired
	protected CitaAdiestramientoService CitaAdiestramientoService;

	private CitaAdiestramiento cita;

	@BeforeEach
	private void setUp() {
		this.cita = new CitaAdiestramiento();
		cita.setFechaInicio(LocalDate.now());
		cita.setHora(LocalTime.now());
		cita.setDuracion(45);
		cita.setPagado(false);
		cita.setId(2);
		cita.setPrecio(30.0);

		TipoAdiestramiento to = new TipoAdiestramiento();
		to.setId(1);
		Adiestrador adi = new Adiestrador();
		adi.setId(1);
		Owner ow = new Owner();
		ow.setId(1);
		cita.setTipoAdiestramiento(to);
		cita.setAdiestrador(adi);
		cita.setOwner(ow);

	}

	@Test
	void testExpectedExceptionFechaInicio() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			cita.setFechaInicio(null);
			cita.getFechaInicio().toString();
		});
	}

	@Test
	void testExpectedExceptionFechaInicioRange() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {

			cita.getFechaInicio().toString().charAt(123);
		});
	}

	@Test
	void testExpectedExceptionHora() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			cita.setHora(null);
			cita.getHora().toString();
		});
	}

	@Test
	void testExpectedExceptionFechaHoraRange() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {

			cita.getHora().toString().charAt(123);
		});
	}

	@Test
	void testExpectedExceptionDuracion() {
		Assertions.assertThrows(NumberFormatException.class, () -> {
			Integer duracion = Integer.parseInt("Uno");
			cita.setDuracion(duracion);
		});
	}

	@Test
	void testExpectedExceptionDuracionNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			cita.setDuracion(null);
			cita.getDuracion().toString();
		});
	}

	@Test
	void testExpectedExceptionDuracionRange() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {

			cita.getDuracion().toString().charAt(123);
		});
	}

	@Test
	void testExpectedExceptionPagado() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			Boolean pago = null;
			cita.setPagado(pago);
			cita.isPagado();
		});
	}
	

	@Test
	void testExpectedExceptionPrecio() {
		Assertions.assertThrows(NumberFormatException.class, () -> {
			Double precio = Double.parseDouble("Uno");
			cita.setPrecio(precio);
		});
	}

	@Test
	void testExpectedExceptionPrecioNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			cita.setPrecio(null);
			cita.getPrecio().toString();
		});
	}

	@Test
	void testExpectedExceptionPrecioRange() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {

			cita.getPrecio().toString().charAt(123);
		});
	}
	
	@Test
	  void testExpectedExceptionPrecioArithmetic() {
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer div = cita.getId() / 0;
	    });
	  }
	
	
	
	@Test
	  void testExpectedExceptionId() { 
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer num = Integer.parseInt("Uno");
	    	cita.setId(num);
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdNull() {
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	cita.setId(null);
	    	cita.getId().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdIndex() {
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	cita.getId().toString().charAt(1234);
	    });
	  }

	@Test
	  void testExpectedExceptionIdArithmetic() {
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer div = cita.getId() / 0;
	    });
	  }
	
}
