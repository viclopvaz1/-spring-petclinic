
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.service.TipoOperacionService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CitaOperacionValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Autowired
	private TipoOperacionService tipoOperacionService;


	@Override
	public boolean supports(final Class<?> clazz) {
		return CitaOperacion.class.isAssignableFrom(clazz);
	}

	public void validateFechas(final Object target) throws FechasException {

		CitaOperacion citaOperacion = (CitaOperacion) target;
		
		if (citaOperacion.getFechaInicio().isBefore(LocalDate.now())
				|| citaOperacion.getFechaInicio().isEqual(LocalDate.now())) {
			throw new FechasException();
		}

	}

	public void validateTipoOperacion(final Object target) throws TipoOperacionException {

		CitaOperacion citaOperacion = (CitaOperacion) target;
		Collection<TipoOperacion> tiposOperacion = (Collection<TipoOperacion>) this.tipoOperacionService.findAll();
	
		if (!tiposOperacion.contains(citaOperacion.getTipoOperacion())) {
			throw new TipoOperacionException();
		}

	}
	
	public void validateDineroMonedero(final Object target) throws PagoException {
		CitaOperacion citaOperacion = (CitaOperacion) target;

		if(citaOperacion.getPrecio() > citaOperacion.getPet().getOwner().getMonedero()) {
			throw new PagoException();
		}
	}

	@Override
	public void validate(final Object target, final Errors errors) {

	}

}
