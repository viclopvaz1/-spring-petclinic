package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaOperacionRepository;
import org.springframework.samples.petclinic.web.TipoOperacionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaOperacionService {
	
	private SpringDataCitaOperacionRepository citaOperacionRepo;
	
//	private TipoOperacionService tipoOperacionService;
	
	@Autowired
	public CitaOperacionService(final SpringDataCitaOperacionRepository stringCitaOperacionRepo) {
		this.citaOperacionRepo = stringCitaOperacionRepo;
//		this.tipoOperacionService = tipoOperacionService;
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
//		return this.citaOperacionRepo.findAll();
		Iterable<CitaOperacion> res = this.citaOperacionRepo.findAll();
		if (StreamSupport.stream(res.spliterator(), false).count() == 0) {
			throw new NoSuchElementException();
		}
		return res;
	}

	@Transactional
	public Iterable<CitaOperacion> findCitaOperacionByTipoOperacion(final String tipoOperacion) throws NoSuchElementException {
		Iterable<CitaOperacion> res = this.citaOperacionRepo.findCitaOperacionByTipoOperacion(tipoOperacion);
//		Collection<TipoOperacion> tiposOperacion = (Collection<TipoOperacion>) this.tipoOperacionService.findAll();
//		// Tipo Operacion validation
//		if (!tiposOperacion.contains(citaOperacion.getTipoOperacion())) {
//			throw new TipoOperacionException();
//		}
		if (StreamSupport.stream(res.spliterator(), false).count() == 0) {
			throw new NoSuchElementException();
		}
		return res;
	}
	
	@Transactional
	public Optional<CitaOperacion> findCitaOperacionById(final int CitaOperacionId) throws NoSuchElementException{
		return this.citaOperacionRepo.findCitaOperacionById(CitaOperacionId);
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
