
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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getOng() {
		return ong;
	}

	public void setOng(String ong) {
		this.ong = ong;
	}

	public Integer getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Integer objetivo) {
		this.objetivo = objetivo;
	}

	public Integer getDineroRecaudado() {
		return dineroRecaudado;
	}

	public void setDineroRecaudado(Integer dineroRecaudado) {
		this.dineroRecaudado = dineroRecaudado;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public List<Donacion> getDonaciones() {
		return donaciones;
	}

	public void setDonaciones(List<Donacion> donaciones) {
		this.donaciones = donaciones;
	}

	@Override
	public String toString() {
		return "Causa [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", ong=" + ong + ", objetivo="
				+ objetivo + ", dineroRecaudado=" + dineroRecaudado + ", valido=" + valido + "]";
	}
	
	

}
