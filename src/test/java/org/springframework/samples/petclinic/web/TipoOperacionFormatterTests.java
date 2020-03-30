package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.service.TipoOperacionService;

@ExtendWith(MockitoExtension.class)
public class TipoOperacionFormatterTests {
	
	@Mock
	private TipoOperacionService tipoOperacionService;

	private TipoOperacionFormatter tipoOperacionFormatter;

	@BeforeEach
	void setup() {
		tipoOperacionFormatter = new TipoOperacionFormatter(tipoOperacionService);
	}

	@Test
	void testPrint() {
		TipoOperacion tipoOperacion = new TipoOperacion();
		tipoOperacion.setName("Cirugia basica");
		String tipoOperacionName = tipoOperacionFormatter.print(tipoOperacion, Locale.ENGLISH);
		assertEquals("Cirugia basica", tipoOperacionName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(tipoOperacionService.findAll()).thenReturn(makeTiposOperacion());
		TipoOperacion tipoOperacion = tipoOperacionFormatter.parse("Cirugia dental", Locale.ENGLISH);
		assertEquals("Cirugia dental", tipoOperacion.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(tipoOperacionService.findAll()).thenReturn(makeTiposOperacion());
		Assertions.assertThrows(ParseException.class, () -> {
			tipoOperacionFormatter.parse("Cirugia craneal", Locale.ENGLISH);
		});
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link PetType}
	 */
	private Collection<TipoOperacion> makeTiposOperacion() {
		Collection<TipoOperacion> tiposOperacion = new ArrayList<>();
		tiposOperacion.add(new TipoOperacion() {
			{
				setName("Cirugia visual");
			}
		});
		tiposOperacion.add(new TipoOperacion() {
			{
				setName("Cirugia dental");
			}
		});
		return tiposOperacion;
	}

}
