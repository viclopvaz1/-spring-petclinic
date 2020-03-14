package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

public class CitasOperaciones {
	
	private List<CitaOperacion> citasOperacion;

	public List<CitaOperacion> getCitaOperacionList() {
		if (citasOperacion == null) {
			citasOperacion = new ArrayList<>();
		}
		return citasOperacion;
	}

}
