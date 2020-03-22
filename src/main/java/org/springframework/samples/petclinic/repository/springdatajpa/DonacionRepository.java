package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.PetType;

public interface DonacionRepository extends CrudRepository<Donacion, Integer>{

}
