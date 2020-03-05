package org.springframework.samples.petclinic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.repository.springdatajpa.CausaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {
	
	@Autowired
	private CausaRepository causaRepo;
	
	@Transactional
	public int causaCount() {
		return (int) causaRepo.count();
	}
	
	@Transactional
	public Iterable<Causa> findAll(){
		return causaRepo.findAll();
	}

}
