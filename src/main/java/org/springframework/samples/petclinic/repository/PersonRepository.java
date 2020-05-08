package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	

}
