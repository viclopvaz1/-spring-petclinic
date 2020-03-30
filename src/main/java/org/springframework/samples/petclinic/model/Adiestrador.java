package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "adiestrador")
public class Adiestrador extends Person{
	
	@Column(name = "telefono")
	private Integer telefono;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PetType tipoAnimal;
	
	@Column(name = "estrellas")
	@Range(min = 0, max = 5)
	private Integer estrellas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adiestrador")
	private Set<CitaAdiestramiento> citasAdiestramiento;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public PetType getTipoAnimal() {
		return tipoAnimal;
	}

	public void setTipoAnimal(PetType tipoAnimal) {
		this.tipoAnimal = tipoAnimal;
	}

	public Integer getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Integer estrellas) {
		this.estrellas = estrellas;
	}

	public Set<CitaAdiestramiento> getCitasAdiestramiento() {
		return citasAdiestramiento;
	}

	public void setCitasAdiestramiento(Set<CitaAdiestramiento> citasAdiestramiento) {
		this.citasAdiestramiento = citasAdiestramiento;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Adiestrador [telefono=" + telefono + ", tipoAnimal=" + tipoAnimal + ", estrellas=" + estrellas
				+ ", user=" + user + "]";
	}
	
	
}
