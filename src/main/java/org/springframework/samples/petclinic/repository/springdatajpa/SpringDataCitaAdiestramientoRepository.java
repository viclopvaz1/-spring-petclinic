package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.repository.CitaAdiestramientoRepository;

public interface SpringDataCitaAdiestramientoRepository extends CitaAdiestramientoRepository, Repository<CitaAdiestramiento, Integer> {

	@Override
	@Query("SELECT ca FROM CitaAdiestramiento ca WHERE ca.owner.id = :ownerId")
	Collection<CitaAdiestramiento> findCitasAdiestramientoByOwnerId(@Param("ownerId") int ownerId);

}
