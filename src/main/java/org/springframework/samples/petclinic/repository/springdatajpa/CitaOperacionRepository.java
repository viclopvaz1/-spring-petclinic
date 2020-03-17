package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.CrudRepository
;
import org.springframework.samples.petclinic.model.CitaOperacion;

public interface CitaOperacionRepository extends CrudRepository<CitaOperacion, Integer> {

}
