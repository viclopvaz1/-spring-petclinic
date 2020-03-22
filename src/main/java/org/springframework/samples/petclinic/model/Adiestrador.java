package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "adiestrador")
public class Adiestrador extends Person{
	
	@Column(name = "telefono")
	private Integer telefono;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PetType tipoAnimal;
	
	@Column(name = "estrellas")
	@Range(min = 0, max = 5)
	private Integer estrellas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adiestrador")
	private Set<CitaAdiestramiento> citasAdiestramiento;

}
