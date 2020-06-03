package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DonacionValidator implements Validator{
	
	private static final String REQUIRED = "required";

	@Override
	public boolean supports(Class<?> clazz) {
		return Donacion.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Causa causa = (Causa) target;
		Integer dineroRecaudado = causa.getDineroRecaudado();
		Integer dineroObjetivo = causa.getObjetivo();
		if (dineroRecaudado > dineroObjetivo) {
			errors.rejectValue("cantidad", REQUIRED+" es superior al dinero objetivo");
		}
		
	}

}
