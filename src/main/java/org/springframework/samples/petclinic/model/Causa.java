package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "causa")
public class Causa extends BaseEntity{
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaInicio;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechFin;
	
	private String ong;
	
	private Integer objetivo;
	
	private Integer dineroRecaudado;

}
