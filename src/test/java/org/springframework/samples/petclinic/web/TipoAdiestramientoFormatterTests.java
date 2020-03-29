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
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;

@ExtendWith(MockitoExtension.class)
public class TipoAdiestramientoFormatterTests {

	@Mock
	private TipoAdiestramientoService tipoAdiestramientoService;

	private TipoAdiestramientoFormatter tipoAdiestramientoFormatter;;

	@BeforeEach
	void setup() {
		tipoAdiestramientoFormatter = new TipoAdiestramientoFormatter(tipoAdiestramientoService);
	}

	@Test
	void testPrint() {
		TipoAdiestramiento tipoAdiestramiento  = new TipoAdiestramiento();
		tipoAdiestramiento.setName("Adiestramiento ppp");
		String tipoAdiestramientoName = tipoAdiestramientoFormatter.print(tipoAdiestramiento, Locale.ENGLISH);
		assertEquals("Adiestramiento ppp", tipoAdiestramientoName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(tipoAdiestramientoService.findAll()).thenReturn(makeTiposAdiestramiento());
		TipoAdiestramiento tipoAdiestramiento = tipoAdiestramientoFormatter.parse("Adiestramiento ppp", Locale.ENGLISH);
		assertEquals("Adiestramiento ppp", tipoAdiestramiento.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(tipoAdiestramientoService.findAll()).thenReturn(makeTiposAdiestramiento());
		Assertions.assertThrows(ParseException.class, () -> {
			tipoAdiestramientoFormatter.parse("Adiestramiento caza", Locale.ENGLISH);
		});
	}

	
	private Collection<TipoAdiestramiento> makeTiposAdiestramiento() {
		Collection<TipoAdiestramiento> tiposAdiestramiento = new ArrayList<>();
		tiposAdiestramiento.add(new TipoAdiestramiento() {
			{
				setName("Adiestramiento ppp");
			}
		});
		tiposAdiestramiento.add(new TipoAdiestramiento() {
			{
				setName("Adiestramiento deportivo");
			}
		});
		return tiposAdiestramiento;
	}

}
