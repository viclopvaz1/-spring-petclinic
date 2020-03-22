package org.springframework.samples.petclinic.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaAdiestramientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaAdiestramientoService {
	
	private SpringDataCitaAdiestramientoRepository citaAdiestramientoRepo;
	
	@Autowired
	public CitaAdiestramientoService(final SpringDataCitaAdiestramientoRepository stringCitaAdiestramientoRepo) {
		this.citaAdiestramientoRepo = stringCitaAdiestramientoRepo;
	}
	
	@Transactional
	public Iterable<CitaAdiestramiento> findAll() {
		return this.citaAdiestramientoRepo.findAll();
	}

	@Transactional
	public Collection<CitaAdiestramiento> findCitaAdiestramientoByOwnerId(final int ownerId) {
		return this.citaAdiestramientoRepo.findCitasAdiestramientoByOwnerId(ownerId);
	}

  	@Transactional
	public void saveCitaAdiestramiento(CitaAdiestramiento citaAdiestramiento) throws DataAccessException {
		this.citaAdiestramientoRepo.save(citaAdiestramiento);

	}
	@Transactional
	public Collection<CitaAdiestramiento> findCitaAdiestramientoByPet(final String tipo) throws DataAccessException {
		return this.citaAdiestramientoRepo.findCitaAdiestramientoByPet(tipo);
	}
  
  	@Transactional
	public int citaAdiestramientoCounnt() {
		return (int) this.citaAdiestramientoRepo.count();
	}
}