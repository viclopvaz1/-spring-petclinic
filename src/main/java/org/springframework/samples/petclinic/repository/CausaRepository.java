
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Causa;

public interface CausaRepository extends CrudRepository<Causa, Integer> {

	Collection<Causa> findCausaByDonaciones(String username) throws DataAccessException;

	Causa findById(int id) throws DataAccessException;

	Collection<Causa> findCausaByValidoFalse() throws DataAccessException;

	Collection<Causa> findCausaByValidoTrue() throws DataAccessException;

}
