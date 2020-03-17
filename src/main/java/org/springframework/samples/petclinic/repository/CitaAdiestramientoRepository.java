package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;

public interface CitaAdiestramientoRepository extends CrudRepository<CitaAdiestramiento, Integer> {
	
	//void save(CitaAdiestramiento citaAdiestramiento) throws DataAccessException;

	Collection<CitaAdiestramiento> findCitaAdiestramientoByAntonio(String tipo) throws DataAccessException;

}
