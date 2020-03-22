/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "vets")
public class Vet extends Person {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty>		specialties;

	@Column(name = "estrellas")
	private Integer				estrellas;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vet")
	private Set<CitaOperacion>	citasOperacion;


	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vet")
	//	private Set<Causa> causas;

	protected Set<Specialty> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(final Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@XmlElement
	public List<Specialty> getSpecialties() {
		List<Specialty> sortedSpecs = new ArrayList<>(this.getSpecialtiesInternal());
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedSpecs);
	}

	public int getNrOfSpecialties() {
		return this.getSpecialtiesInternal().size();
	}

	public void addSpecialty(final Specialty specialty) {
		this.getSpecialtiesInternal().add(specialty);
	}

	public Integer getEstrellas() {
		return this.estrellas;
	}

	public void setEstrellas(final Integer estrellas) {
		this.estrellas = estrellas;
	}

//	public Set<CitaOperacion> getCitasOperacionInternal() {
//		if(this.citasOperacion == null) {
//			this.citasOperacion = new HashSet<>();
//		}
//		return this.citasOperacion;
//	}
//
//	public void setCitasOperacionInternal(Set<CitaOperacion> citasOperacion) {
//		this.citasOperacion = citasOperacion;
//	}
//
//	public List<CitaOperacion> getCitasOperacion() {
//		List<CitaOperacion> sortedcitasOperacion = new ArrayList<>(getCitasOperacionInternal());
//		PropertyComparator.sort(sortedcitasOperacion, new MutableSortDefinition("date", false, false));
//		return Collections.unmodifiableList(sortedcitasOperacion);
//	}

	public Set<Causa> getCausas() {
		return causas;
	}
	
	public void setCausas(Set<Causa> causas) {
		this.causas = causas;
	}
	

	public Set<CitaOperacion> getCitasOperacion() {
		return this.citasOperacion;
	}

	public void setCitasOperacion(final Set<CitaOperacion> citasOperacion) {
		this.citasOperacion = citasOperacion;
	}
}
