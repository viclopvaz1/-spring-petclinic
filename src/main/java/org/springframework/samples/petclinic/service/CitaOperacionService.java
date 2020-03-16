package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaOperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaOperacionService {
	
	private SpringDataCitaOperacionRepository citaOperacionRepo;
	
	@Autowired
	public CitaOperacionService(final SpringDataCitaOperacionRepository stringCitaOperacionRepo) {
		this.citaOperacionRepo = stringCitaOperacionRepo;
	}
	
	@Transactional
	public Iterable<CitaOperacion> findAll() {
		return this.citaOperacionRepo.findAll();
	}

	@Transactional
	public Collection<CitaOperacion> findCitaOperacionByTipoOperacion(final String tipoOperacion) {
		return this.citaOperacionRepo.findCitaOperacionByTipoOperacion(tipoOperacion);
	}

}
