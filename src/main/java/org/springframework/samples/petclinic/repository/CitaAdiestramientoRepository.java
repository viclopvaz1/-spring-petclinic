package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;

public interface CitaAdiestramientoRepository extends CrudRepository<CitaAdiestramiento, Integer> {
	
	//void save(CitaAdiestramiento citaAdiestramiento) throws DataAccessException;

	Collection<CitaAdiestramiento> findCitaAdiestramientoByPet(String tipo) throws DataAccessException;
  
	Collection<CitaAdiestramiento> findCitasAdiestramientoByOwnerId(int ownerId) throws DataAccessException;

	CitaAdiestramiento findCitaAdiestramientoById(int citaAdiestramientoId) throws DataAccessException;
	
}
