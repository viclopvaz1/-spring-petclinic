
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Causa;

public interface CausaRepository extends CrudRepository<Causa, Integer> {

	Collection<Causa> findCausaByOng(String ong) throws DataAccessException;

	Collection<Causa> findCausaByValidoTrue() throws DataAccessException;

}
