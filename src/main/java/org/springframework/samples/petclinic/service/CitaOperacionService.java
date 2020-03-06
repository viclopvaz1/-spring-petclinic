package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.CitaOperacionRepository;
import org.springframework.stereotype.Service;

@Service
public class CitaOperacionService {
	
	@Autowired
	CitaOperacionRepository citasOperacionesRepo;
	
	@Transactional
	public int citaOperacionCount() {
		return (int) citasOperacionesRepo.count();
	}
	
	@Transactional
	public Iterable<CitaOperacion> findAll() {
		return citasOperacionesRepo.findAll();
	}

}
