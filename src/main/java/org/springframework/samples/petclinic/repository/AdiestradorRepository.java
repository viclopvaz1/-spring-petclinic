
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adiestrador;

public interface AdiestradorRepository extends CrudRepository<Adiestrador, Integer> {

	Collection<Adiestrador> findAdiestradorByEstrellas(Integer estrellas) throws DataAccessException;
	
	Collection<Adiestrador> findAll() throws DataAccessException;
	
    Adiestrador findAdiestradorByUser(@Param("username") String username);

	Adiestrador findByUser(String username) throws DataAccessException;
}
