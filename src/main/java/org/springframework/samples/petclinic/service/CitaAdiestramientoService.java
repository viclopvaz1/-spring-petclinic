package org.springframework.samples.petclinic.service;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.CitaAdiestramientoRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaAdiestramientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaAdiestramientoService {

	private CitaAdiestramientoRepository citaAdiestramientoRepository;

	@Autowired
    public CitaAdiestramientoService(final SpringDataCitaAdiestramientoRepository stringCitaRepo) {
        this.citaAdiestramientoRepository = stringCitaRepo;
    }
	
	public CitaAdiestramientoService(CitaAdiestramientoRepository citaAdiestramientoRepository) {
		this.citaAdiestramientoRepository = citaAdiestramientoRepository;
	}

	@Transactional
	public int citaAdiestramientoCounnt() {
		return (int) this.citaAdiestramientoRepository.count();
	}

	@Transactional
	public Iterable<CitaAdiestramiento> findAll() {
		return this.citaAdiestramientoRepository.findAll();
	}

	@Transactional
	public void saveCitaAdiestramiento(CitaAdiestramiento citaAdiestramiento) throws DataAccessException {
		this.citaAdiestramientoRepository.save(citaAdiestramiento);

	}
	@Transactional
	public Collection<CitaAdiestramiento> findCitaAdiestramientoByAntonio(final String tipo) throws DataAccessException {
		return this.citaAdiestramientoRepository.findCitaAdiestramientoByAntonio(tipo);
	}

	
	
}
