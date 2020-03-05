package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.repository.springdatajpa.AdiestradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdiestradorService {
	
	@Autowired
	private AdiestradorRepository adiestradorRepo;
	
	@Transactional
	public int adiestradorCount() {
		return (int) adiestradorRepo.count();
	}
	
	@Transactional
	public Iterable<Adiestrador> findAll(){
		return adiestradorRepo.findAll();
	}
	

}
