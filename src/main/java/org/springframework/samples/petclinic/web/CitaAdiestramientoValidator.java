package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CitaAdiestramientoValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Autowired
	private TipoAdiestramientoService tipoAdiestramientoService;


	@Override
	public boolean supports(final Class<?> clazz) {
		return CitaAdiestramiento.class.isAssignableFrom(clazz);
	}

	public void validateFechas(final Object target) throws FechasException {

		CitaAdiestramiento citaAdiestramiento = (CitaAdiestramiento) target;
	
		if (citaAdiestramiento.getFechaInicio().isBefore(LocalDate.now())
				|| citaAdiestramiento.getFechaInicio().isEqual(LocalDate.now())) {
			throw new FechasException();
		}

	}

	public void validateTipoAdiestramiento(final Object target) throws TipoAdiestramientoException {

		CitaAdiestramiento citaOperacion = (CitaAdiestramiento) target;
		Collection<TipoAdiestramiento> tiposAdiestramientos = (Collection<TipoAdiestramiento>) this.tipoAdiestramientoService.findAll();
		
		if (!tiposAdiestramientos.contains(citaOperacion.getTipoAdiestramiento())) {
			throw new TipoAdiestramientoException();
		}

	}
	
	public void validateDineroMonedero(final Object target) throws PagoException {
		CitaAdiestramiento citaAdiestramiento = (CitaAdiestramiento) target;
	
		if(citaAdiestramiento.getPrecio() > citaAdiestramiento.getPet().getOwner().getMonedero()) {
			throw new PagoException();
		}
	}

	@Override
	public void validate(final Object target, final Errors errors) {

	}

}
