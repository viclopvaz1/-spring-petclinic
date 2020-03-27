package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;

public interface TipoAdiestramientoRepository extends CrudRepository<TipoAdiestramiento, Integer>{
	TipoAdiestramiento findTipoAdiestramientoByName(String name);


}
