package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.CitaAdiestramientoRepository;

public interface SpringDataCitaAdiestramientoRepository extends CitaAdiestramientoRepository, Repository<CitaAdiestramiento, Integer> {

	@Override
	@Query("SELECT ca FROM CitaAdiestramiento ca WHERE ca.owner.id = :ownerId")
	Collection<CitaAdiestramiento> findCitasAdiestramientoByOwnerId(@Param("ownerId") int ownerId);
//
	@Override
	@Query("SELECT citaAdiestramiento FROM CitaAdiestramiento citaAdiestramiento WHERE citaAdiestramiento.pet.type.name LIKE :tipoPet%")
	Collection<CitaAdiestramiento> findCitaAdiestramientoByPet(@Param("tipoPet") String tipoPet);
	
//	
//	@Override
//	@Query("SELECT DISTINCT ca FROM CitaAdiestramiento citaAdiestramiento  LEFT JOIN FETCH citaAdiestramiento.pet.type.name WHERE LIKE :tipoPet%")
//	Collection<CitaAdiestramiento> findCitaAdiestramientoByPet(@Param("tipoPet") String tipoPet);
	//SELECT DISTINCT v FROM Vet v LEFT JOIN FETCH v.specialities sp
	
	@Query("SELECT DISTINCT ca FROM CitaAdiestramiento ca JOIN FETCH ca.tipoAdiestramiento ta")
	Collection<CitaAdiestramiento> findCitaAdiestramientoAll();
	
	@Override
	@Query("SELECT citaAdiestramiento FROM CitaAdiestramiento citaAdiestramiento  WHERE citaAdiestramiento.id = :citaAdiestramientoId")
	CitaAdiestramiento findCitaAdiestramientoById(@Param("citaAdiestramientoId") int citaAdiestramientoId);
	


}