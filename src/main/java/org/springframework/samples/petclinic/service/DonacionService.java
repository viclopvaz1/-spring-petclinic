package org.springframework.samples.petclinic.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.DonacionRepository;
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
	public void saveDonacion(Donacion donacion) throws DataAccessException, IllegalArgumentException{
//		if(donacion.getCantidad() < 0) {
//			throw new IllegalArgumentException();
//		}
		donacionRepo.save(donacion);		

	}	
	
	@Transactional
	public Donacion findById(int id) throws NoSuchElementException{
		Donacion donacion = donacionRepo.findById(id).orElse(null);
		if (donacion == null) {
			throw new NoSuchElementException();
		}
		return donacion;
	}

}
