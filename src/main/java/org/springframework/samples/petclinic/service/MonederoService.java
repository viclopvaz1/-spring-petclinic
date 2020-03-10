package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.springdatajpa.MonederoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonederoService {

	@Autowired
	private MonederoRepository monederoRepo;
	
	@Transactional
	public int monederoCount() {
		return (int) monederoRepo.count();
	}
	
}
