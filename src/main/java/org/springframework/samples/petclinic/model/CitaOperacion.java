package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "citas_Operaciones")
@Data
public class CitaOperacion extends Cita {
	
	@ManyToOne
	@JoinColumn(name = "vet_id")
	private Vet vet;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "tipoOperacion_id")
	private TipoOperacion tipoOperacion;
	
	@Column(name = "cantidadPersonal")
	@NotNull
	@Min(1)
	private Double cantidadPersonal;

}