
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;

public interface SpringDataUserRepository extends UserRepository, Repository<User, Integer> {

	@Override
	@Query("SELECT user FROM User user WHERE user.id =:id")
	User findById(@Param("id") String id);

}
