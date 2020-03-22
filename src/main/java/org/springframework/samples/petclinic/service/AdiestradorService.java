package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdiestradorService {
	

	private SpringDataAdiestradorRepository adiestradorRepo;
	
	@Autowired
	public AdiestradorService(final SpringDataAdiestradorRepository stringAdiestradorRepo) {
		this.adiestradorRepo = stringAdiestradorRepo;
	}
	
	@Transactional
	public int adiestradorCount() {
		return (int) adiestradorRepo.count();
	}
	
	@Transactional
	public Iterable<Adiestrador> findAll(){
		return adiestradorRepo.findAll();
	}
	
	@Transactional
	public Collection<Adiestrador> findAdiestradorByEstrellas(final Integer estrellas){
			return adiestradorRepo.findAdiestradorByEstrellas(estrellas);
		
	}

}
