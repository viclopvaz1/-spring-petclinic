package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
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

}
