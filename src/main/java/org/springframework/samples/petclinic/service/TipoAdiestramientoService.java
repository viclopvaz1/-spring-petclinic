package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataTipoAdiestramientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoAdiestramientoService {
	private SpringDataTipoAdiestramientoRepository tipoAdiestramientoRepo;

	@Autowired
	public TipoAdiestramientoService(final SpringDataTipoAdiestramientoRepository stringTipoAdiestramientoRepo) {
		this.tipoAdiestramientoRepo = stringTipoAdiestramientoRepo;
	}

	@Transactional
	public TipoAdiestramiento findTipoAdiestramientoByName(final String name) {
		return this.tipoAdiestramientoRepo.findTipoAdiestramientoByName(name);

	}

	@Transactional
	public Iterable<TipoAdiestramiento> findAll() {
		return this.tipoAdiestramientoRepo.findAll();
	}

}
