package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;
import org.springframework.stereotype.Component;

@Component
public class TipoAdiestramientoFormatter implements Formatter<TipoAdiestramiento> {

	private final TipoAdiestramientoService tipoAdiestramientoService;

	@Autowired
	public TipoAdiestramientoFormatter(TipoAdiestramientoService tipoAdiestramientoService) {
		this.tipoAdiestramientoService = tipoAdiestramientoService;
	}

	@Override
	public String print(TipoAdiestramiento tipoAdiestramiento, Locale locale) {
		return tipoAdiestramiento.getName();
	}

    @Override
    public TipoAdiestramiento parse(String text, Locale locale) throws ParseException {
        Collection<TipoAdiestramiento> tipoAdiestramiento = (Collection<TipoAdiestramiento>) this.tipoAdiestramientoService.findAll();
        for (TipoAdiestramiento tipo : tipoAdiestramiento) {
            if (tipo.getName().equals(text)) {
                return tipo;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
