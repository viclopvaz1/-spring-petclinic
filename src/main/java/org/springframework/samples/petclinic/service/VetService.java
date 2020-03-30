/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataVetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private SpringDataVetRepository vetRepository;


	@Autowired
	public VetService(final SpringDataVetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Vet> findVets() throws DataAccessException {
		return this.vetRepository.findAll();
	}

	@Transactional
	public Vet findVetByUser(final String username) throws DataAccessException {
		return this.vetRepository.findVetByUser(username);
	}

	@Transactional(readOnly = true)
	public Vet findVetById(final int id) throws DataAccessException {
		return this.vetRepository.findById(id);
	}


	//	@Transactional(readOnly = true)
	//	public Collection<CitaOperacion> findCitaOperacionByTipoOperacion(String tipoOperacion, int vetId) throws DataAccessException {
	//		return vetRepository.findByTipoOperacion(tipoOperacion, vetId);
	//	}

	public Vet findVetByUser(String username) {
		return  vetRepository.findVetByUser(username);
	}

//	@Transactional(readOnly = true)
//	public Collection<CitaOperacion> findCitaOperacionByTipoOperacion(String tipoOperacion, int vetId) throws DataAccessException {
//		return vetRepository.findByTipoOperacion(tipoOperacion, vetId);
//	}

}
