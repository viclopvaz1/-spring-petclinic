package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;

public interface CitaAdiestramientoRepository extends CrudRepository<CitaAdiestramiento, Integer> {

	Collection<CitaAdiestramiento> findCitasAdiestramientoByOwnerId(int ownerId) throws DataAccessException;

}