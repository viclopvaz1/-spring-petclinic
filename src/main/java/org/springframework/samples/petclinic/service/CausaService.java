
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCausaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {

	private SpringDataCausaRepository causaRepo;


	@Autowired
	public CausaService(final SpringDataCausaRepository stringCausaRepo) {
		this.causaRepo = stringCausaRepo;
	}

	@Transactional
	public int causaCount() {
		return (int) this.causaRepo.count();
	}

	@Transactional
	public Iterable<Causa> findAll() {
		return this.causaRepo.findAll();
	}

	@Transactional
	public Iterable<Causa> findCausaByOng(final String username) {
		return this.causaRepo.findCausaByOng(username);
	}
}
