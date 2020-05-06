package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Donacion;

public interface DonacionRepository extends CrudRepository<Donacion, Integer>{

}
