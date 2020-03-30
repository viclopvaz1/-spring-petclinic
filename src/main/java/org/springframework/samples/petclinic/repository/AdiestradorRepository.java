package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Vet;

public interface AdiestradorRepository extends CrudRepository<Adiestrador, Integer> {
	
	Collection<Adiestrador> findAdiestradorByEstrellas(Integer estrellas) throws DataAccessException;
	
	Adiestrador findByUser(String username) throws DataAccessException;

	Adiestrador findByUser(String username) throws DataAccessException;

	Adiestrador findById(int id) throws DataAccessException;

}
