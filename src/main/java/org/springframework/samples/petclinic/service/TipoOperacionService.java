package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataTipoOperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoOperacionService {
	
private SpringDataTipoOperacionRepository tipoOperacionRepo;
	
	@Autowired
	public TipoOperacionService(final SpringDataTipoOperacionRepository stringTipoOperacionRepo) {
		this.tipoOperacionRepo = stringTipoOperacionRepo;
	}
	
	@Transactional
	public TipoOperacion findTipoOperacionByName(final String name) {
		return this.tipoOperacionRepo.findTipoOperacionByName(name);
	}
	

	
	@Transactional
	public Iterable<TipoOperacion> findAll() {
		return this.tipoOperacionRepo.findAll();
	}

}
