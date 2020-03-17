package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.repository.CitaAdiestramientoRepository;

public interface SpringDataCitaAdiestramientoRepository  extends CitaAdiestramientoRepository, Repository<CitaAdiestramiento, Integer> { 

	
//	@Query("SELECT ptype FROM PetType ctype ORDER BY ptype.name")
//	@Query("SELECT citaAdi FROM CitaAdiestramiento citaAdi WHERE citaAdi.id IN(SELECT pet.type.id FROM Pet pet)")
//	@Override
//	@Query("SELECT DISTINCT cita FROM CitaAdiestramiento cita left join fetch cita.pet WHERE cita.pet.type LIKE :tipo%")
//	Collection<CitaAdiestramiento> findByType(String tipo) throws DataAccessException;

	


	@Override
	@Query("SELECT citaAdiestramiento FROM CitaAdiestramiento citaAdiestramiento WHERE citaAdiestramiento.pet.type.name LIKE :tipoPet%")
	Collection<CitaAdiestramiento> findCitaAdiestramientoByPet(@Param("tipoPet") String tipoPet);
	//"SELECT citaOperacion FROM CitaOperacion citaOperacion WHERE citaOperacion.tipoOperacion.name LIKE :tipoOperacion%
}
//@Override
//@Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
//Collection<Owner> findByLastName(@Param("lastName") String lastName); left join fetch cita