
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
	public void saveCausa(final Causa causa) {
		this.causaRepo.save(causa);
	}

	@Transactional
	public void deleteCausa(final Causa causa) {
		this.causaRepo.delete(causa);
	}

	@Transactional
	public Iterable<Causa> findAll() {
		return this.causaRepo.findCausaByValidoTrue();
	}

	@Transactional
	public Causa findCausaById(final int id) {
		Causa causa;
		causa = this.causaRepo.findById(id).orElse(null);
		return causa;
	}

	@Transactional
	public Iterable<Causa> findCausaByOng(final String username) {
		return this.causaRepo.findCausaByOng(username);
	}
}
