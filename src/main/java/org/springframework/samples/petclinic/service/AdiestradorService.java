
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdiestradorService {

	private SpringDataAdiestradorRepository adiestradorRepo;


	@Autowired
	public AdiestradorService(final SpringDataAdiestradorRepository stringAdiestradorRepo) {
		this.adiestradorRepo = stringAdiestradorRepo;
	}

	@Transactional
	public int adiestradorCount() {
		return (int) this.adiestradorRepo.count();
	}

	@Transactional
	public Iterable<Adiestrador> findAll() {
		return this.adiestradorRepo.findAll();
	}

	@Transactional
	public Collection<Adiestrador> findAdiestradorByEstrellas(final Integer estrellas) {
		return this.adiestradorRepo.findAdiestradorByEstrellas(estrellas);

	}

	@Transactional(readOnly = true)
	public Adiestrador findByUser(final String username) throws DataAccessException {
		return this.adiestradorRepo.findByUser(username);
	}

}
