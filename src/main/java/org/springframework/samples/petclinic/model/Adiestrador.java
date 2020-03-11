package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "adiestrador")
public class Adiestrador extends Person{
	
	@Column(name = "telefono")
	private Integer telefono;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PetType tipoAnimal;
	
	@Column(name = "estrellas")
	private Integer estrellas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adiestrador")
	private Set<CitaAdiestramiento> citasAdiestramiento;

}
