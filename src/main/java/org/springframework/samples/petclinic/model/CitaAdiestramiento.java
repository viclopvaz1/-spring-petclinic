package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "citaAdiestramiento")
public class CitaAdiestramiento extends Cita {
	
	@ManyToOne
	@JoinColumn(name = "tipoAdiestramiento_id")
	private TipoAdiestramiento tipoAdiestramiento;

}
