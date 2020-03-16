
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "causa")
public class Causa extends BaseEntity {

	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate		fechaInicio;

	@Column(name = "fecha_fin")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate		fechaFin;

	@Column(name = "ong")
	private String			ong;

	@Column(name = "objetivo")
	private Integer			objetivo;

	@Column(name = "dinero_recaudado")
	private Integer			dineroRecaudado;

	@Column(name = "valido")
	private boolean			valido;

	//	@ManyToOne
	//	@JoinColumn(name = "vet_id")
	//	private Vet				vet;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "causa")
	private List<Donacion>	donaciones;

}
