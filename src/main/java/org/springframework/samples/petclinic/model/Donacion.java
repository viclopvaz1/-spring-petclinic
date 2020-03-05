package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "donacion")
public class Donacion extends BaseEntity {
	
	private Integer cantidad;
	
	@ManyToOne
	private User persona;
	
	@ManyToOne
	private Causa causa;

}
