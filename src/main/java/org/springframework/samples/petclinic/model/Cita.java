package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class Cita extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@Column(name = "fecha")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fechaInicio;
	
	@Column(name = "hora")
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	@Column(name = "duracion")
	@NotNull
	@Min(1)
	private Integer duracion;
	
	@Column(name = "precio")
	@Min(1)
	@NotNull
	private Double precio;
	
	@Column(name = "pagado")
	private boolean pagado;

}
