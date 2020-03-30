package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "citas_Adiestramiento")
@Data
@EqualsAndHashCode(callSuper=false)
public class CitaAdiestramiento extends Cita {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ManyToOne
	@JoinColumn(name = "tipoAdiestramiento_id")
	private TipoAdiestramiento tipoAdiestramiento;

	@ManyToOne
	@JoinColumn(name = "adiestrador_id")
	private Adiestrador adiestrador;

}
