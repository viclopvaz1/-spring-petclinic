package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.springdatajpa.DonacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonacionService {
	
	@Autowired
	private DonacionRepository donacionRepo;
	
	@Transactional
	public int donacionCount() {
		return (int) donacionRepo.count();
	}
	
	@Transactional
	public void saveDonacion(Donacion donacion) throws DataAccessException {
		donacionRepo.save(donacion);		

	}	
	
	@Transactional
	public Donacion findById(int id) {
		return donacionRepo.findById(id).orElse(null);
	}

}
