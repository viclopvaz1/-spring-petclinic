
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.AdiestradorRepository;

public interface SpringDataAdiestradorRepository extends AdiestradorRepository, Repository<Adiestrador, Integer> {

	@Override
	@Query("SELECT adiestrador FROM Adiestrador adiestrador WHERE adiestrador.estrellas = 5")
	Collection<Adiestrador> findAdiestradorByEstrellas(@Param("estrellas") Integer estrellas);
	
	@Override
    @Query("SELECT adiestrador FROM Adiestrador adiestrador WHERE adiestrador.user.username LIKE :username%")
    Adiestrador findAdiestradorByUser(@Param("username") String username);

	@Override
	@Query("SELECT adiestrador FROM Adiestrador adiestrador WHERE adiestrador.user.username LIKE :username")
	Adiestrador findByUser(@Param("username") String username);

}
