package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaOperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaOperacionService {
	
	private SpringDataCitaOperacionRepository citaOperacionRepo;
	
	@Autowired
	public CitaOperacionService(final SpringDataCitaOperacionRepository stringCitaOperacionRepo) {
		this.citaOperacionRepo = stringCitaOperacionRepo;
	}
	
//	@Transactional
//	public TipoOperacion findTipoOperacionByName(final String name) {
//		return this.citaOperacionRepo.findTipoOperacionByName(name);
//	}
	
//	@Transactional(readOnly = true)
//	public Collection<TipoOperacion> findTiposOperaciones() throws DataAccessException {
//		return this.citaOperacionRepo.findTiposOperaciones();
//	}
	
	@Transactional
	public Iterable<CitaOperacion> findAll() {
		return this.citaOperacionRepo.findAll();
	}

	@Transactional
	public Collection<CitaOperacion> findCitaOperacionByTipoOperacion(final String tipoOperacion) {
		return this.citaOperacionRepo.findCitaOperacionByTipoOperacion(tipoOperacion);
	}
	
	@Transactional
	public CitaOperacion findCitaOperacionById(final int citaAdiestramientoId) {
		return this.citaOperacionRepo.findCitaOperacionById(citaAdiestramientoId);
	}
	
	@Transactional
	public void saveCitaOperacion(final CitaOperacion citaOperacion) {
		this.citaOperacionRepo.save(citaOperacion);
	}
	
	@Transactional
	public void deleteCitaOperacion(final CitaOperacion citaOperacion) {
		this.citaOperacionRepo.delete(citaOperacion);
	}
}
