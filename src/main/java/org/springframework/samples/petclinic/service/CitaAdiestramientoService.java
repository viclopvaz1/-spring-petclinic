package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaAdiestramientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaAdiestramientoService {
	
	private SpringDataCitaAdiestramientoRepository citaAdiestramientoRepo;
	
	@Transactional
    public Collection<CitaAdiestramiento> findCitaAdiestramientoByOwnerId(final int ownerId) {
        return this.citaAdiestramientoRepo.findCitasAdiestramientoByOwnerId(ownerId);
    }

}
