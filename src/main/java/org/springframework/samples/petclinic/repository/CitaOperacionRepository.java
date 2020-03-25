package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;

public interface CitaOperacionRepository extends CrudRepository<CitaOperacion, Integer> {

	Collection<CitaOperacion> findCitaOperacionByTipoOperacion(String tipoOperacion) throws DataAccessException;

	CitaOperacion findCitaOperacionById(int citaOperacionId);
	
//	TipoOperacion findTipoOperacionByName(String name);
	
//	Collection<TipoOperacion> findTiposOperaciones() throws DataAccessException;

}
