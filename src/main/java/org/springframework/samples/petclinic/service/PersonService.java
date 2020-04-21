package org.springframework.samples.petclinic.service;

import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

	private PersonRepository personRepository;


	@Transactional
	public void savePerson(final Person person) throws DataAccessException {
		this.personRepository.save(person);
	}

	@Transactional
	public Person findPersonById(Integer id) {
		return personRepository.findById(id).orElse(null);
	}

}
