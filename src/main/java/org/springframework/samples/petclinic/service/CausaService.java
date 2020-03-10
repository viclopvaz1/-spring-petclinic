
package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCausaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {

	//	private CausaRepository causaRepo;
	private SpringDataCausaRepository causaRepo;


	@Transactional
	public int causaCount() {
		return (int) this.causaRepo.count();
	}

	@Transactional
	public Iterable<Causa> findAll() {
		return this.causaRepo.findAll();
	}

	@Transactional
	public Iterable<Causa> causesWhereIDonated(final String username) {
		System.out.println("result find by id: " + this.causaRepo.findById(1));
		return this.causaRepo.causesWhereIDonated(username);
	}
}
