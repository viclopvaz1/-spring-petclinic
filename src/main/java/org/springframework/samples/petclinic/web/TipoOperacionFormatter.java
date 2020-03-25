package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.stereotype.Component;

@Component
public class TipoOperacionFormatter implements Formatter<TipoOperacion> {

	private final CitaOperacionService citaOperacionService;

	@Autowired
	public TipoOperacionFormatter(CitaOperacionService citaOperacionService) {
		this.citaOperacionService = citaOperacionService;
	}

	@Override
	public String print(TipoOperacion tipoOperacion, Locale locale) {
		return tipoOperacion.getName();
	}

	@Override
	public TipoOperacion parse(String text, Locale locale) throws ParseException {
		Collection<TipoOperacion> tipoOperacion = new HashSet<TipoOperacion>();
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		for(CitaOperacion co : citasOperaciones) {
			tipoOperacion.add(co.getTipoOperacion());
		}
		for (TipoOperacion tipo : tipoOperacion) {
			if (tipo.getName().equals(text)) {
				return tipo;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
}
