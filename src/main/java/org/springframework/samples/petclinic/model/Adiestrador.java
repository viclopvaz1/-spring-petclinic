package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "adiestrador")
public class Adiestrador extends Person{
	
	private Integer telefono;
	
	@OneToOne
	private PetType tipoAnimal;
	
	private Integer estrellas;

}
