package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoOperacion;

public interface TipoOperacionRepository extends CrudRepository<TipoOperacion, Integer> {
	
	TipoOperacion findTipoOperacionByName(String name);
	
//	Collection<TipoOperacion> findTiposOperaciones() throws DataAccessException;

}
