package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.CitaOperacionRepository;

public interface SpringDataCitaOperacionRepository extends CitaOperacionRepository, Repository<CitaOperacion, Integer> {

	@Override
	@Query("SELECT citaOperacion FROM CitaOperacion citaOperacion WHERE citaOperacion.tipoOperacion.name LIKE :tipoOperacion%")
//	@Query("SELECT citaOperacion FROM CitaOperacion citaOperacion left join fetch citaOperacion.tipoOperacion tipo WHERE tipo.name LIKE :tipoOperacion%")
	Collection<CitaOperacion> findCitaOperacionByTipoOperacion(@Param("tipoOperacion") String tipoOperacion);
	
	@Override
	@Query("SELECT citaOperacion FROM CitaOperacion citaOperacion WHERE citaOperacion.id = :citaOperacionId")
	CitaOperacion findCitaOperacionById(@Param("citaOperacionId") int citaOperacionId);

}
