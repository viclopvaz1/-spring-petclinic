
package org.springframework.samples.petclinic.service;


import java.util.Collection;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.repository.DonacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonacionService {

	private DonacionRepository donacionRepo;


	@Autowired
	public DonacionService(final DonacionRepository donacionRepo) {
		this.donacionRepo = donacionRepo;
	}

	@Transactional
	public int donacionCount() {
		return (int) this.donacionRepo.count();
	}

	@Transactional
	public void saveDonacion(final Donacion donacion) throws DataAccessException {
		this.donacionRepo.save(donacion);

	}

	@Transactional
	public void deleteDonacion(final Donacion donacion) throws DataAccessException {
		this.donacionRepo.delete(donacion);

	}
	
	@Transactional
	public void deleteDonacionAll(final Collection<Donacion> donacion) throws DataAccessException {
	     this.donacionRepo.deleteAll(donacion);
	 
	}

	@Transactional
	public Donacion findById(int id) throws NoSuchElementException{
		Donacion donacion = donacionRepo.findById(id).orElse(null);
		if (donacion == null) {
			throw new NoSuchElementException();
		}
		return donacion;
	}

}
