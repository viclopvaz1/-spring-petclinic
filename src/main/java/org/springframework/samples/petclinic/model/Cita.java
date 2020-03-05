package org.springframework.samples.petclinic.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class Cita extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
//	@ManyToOne
//	@JoinColumn(name = "person_id")
//	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;
	
	@Column(name = "fecha")        
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private LocalDateTime fechaInicio;
	
	@Column(name = "duracion")        
	private Integer fechaFin;
	
	@Column(name = "precio")
	@Min(1)
	private Double precio;

//	public Pet getPet() {
//		return pet;
//	}
//
//	public void setPet(Pet pet) {
//		this.pet = pet;
//	}
//
//	public Person getPerson() {
//		return person;
//	}
//
//	public void setPerson(Person person) {
//		this.person = person;
//	}
//
//	public PetType getType() {
//		return type;
//	}
//
//	public void setType(PetType type) {
//		this.type = type;
//	}
//
//	public LocalDate getFecha() {
//		return fecha;
//	}
//
//	public void setFecha(LocalDate fecha) {
//		this.fecha = fecha;
//	}
//
//	public LocalTime getDuracion() {
//		return duracion;
//	}
//
//	public void setDuracion(LocalTime duracion) {
//		this.duracion = duracion;
//	}
//
//	public Double getPrecio() {
//		return precio;
//	}
//
//	public void setPrecio(Double precio) {
//		this.precio = precio;
//	}

}
