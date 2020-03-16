package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaOperacion;

public interface CitaOperacionRepository extends CrudRepository<CitaOperacion, Integer> {

	Collection<CitaOperacion> findCitaOperacionByTipoOperacion(String tipoOperacion) throws DataAccessException;

}
