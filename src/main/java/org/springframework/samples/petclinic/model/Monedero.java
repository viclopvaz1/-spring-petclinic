package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "monedero")
public class Monedero extends NamedEntity{
	
	private Integer cantidad;

}
