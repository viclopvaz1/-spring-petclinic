package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "citasoperaciones")
public class CitaOperacion extends Cita {
	
	@ManyToOne
	@JoinColumn(name = "tipoOperacion_id")
	private TipoOperacion tipoOperacion;
	
	@Column(name = "cantidadPersonal")
	private Double cantidadPersonal;

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Double getCantidadPersonal() {
		return cantidadPersonal;
	}

	public void setCantidadPersonal(Double cantidadPersonal) {
		this.cantidadPersonal = cantidadPersonal;
	}

}
