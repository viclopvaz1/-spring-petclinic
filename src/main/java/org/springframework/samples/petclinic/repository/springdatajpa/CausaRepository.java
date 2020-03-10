package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Causa;

public interface CausaRepository extends CrudRepository<Causa, Integer>{
	

}
