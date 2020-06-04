
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

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
	private UserService					userService;

	@Autowired
	private AuthoritiesService			authoritiesService;
	
	@Autowired
	public AdiestradorService(final SpringDataAdiestradorRepository stringAdiestradorRepo) {
		this.adiestradorRepo = stringAdiestradorRepo;
	}

	@Transactional
	public int adiestradorCount() throws NoSuchElementException{
		return (int) adiestradorRepo.count();

	}

	@Transactional
	public Iterable<Adiestrador> findAll() throws NoSuchElementException{
		Iterable<Adiestrador> res = this.adiestradorRepo.findAll();
		if (StreamSupport.stream(res.spliterator(), false).count() == 0) {
			throw new NoSuchElementException();
		}
		return res;
	}

	@Transactional
	public Collection<Adiestrador> findAdiestradorByEstrellas(final Integer estrellas) throws NoSuchElementException{
		Collection<Adiestrador> res = this.adiestradorRepo.findAdiestradorByEstrellas(estrellas);
		if (StreamSupport.stream(res.spliterator(), false).count() == 0) {
			throw new NoSuchElementException();
		}
		return res;
	}
	
    @Transactional
    public Adiestrador findAdiestradorByUser(String username) {
    	return  adiestradorRepo.findAdiestradorByUser(username);
    }

	@Transactional(readOnly = true)
	public Adiestrador findAdiestradorById(int id) throws DataAccessException {
		return adiestradorRepo.findById(id);
	}
	
	@Transactional
	public void saveAdiestrador(final Adiestrador adiestrador) throws DataAccessException {
		this.adiestradorRepo.save(adiestrador);
	}
}
