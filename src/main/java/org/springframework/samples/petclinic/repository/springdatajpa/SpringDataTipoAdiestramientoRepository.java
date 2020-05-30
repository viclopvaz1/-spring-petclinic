package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.repository.TipoAdiestramientoRepository;

public interface SpringDataTipoAdiestramientoRepository
		extends TipoAdiestramientoRepository, Repository<TipoAdiestramiento, Integer> {

	@Override
	@Query("SELECT tipoAdiestramiento FROM TipoAdiestramiento tipoAdiestramiento WHERE tipoAdiestramiento.name LIKE :name%")
	TipoAdiestramiento findTipoAdiestramientoByName(@Param("name") String name);


        
	

}
