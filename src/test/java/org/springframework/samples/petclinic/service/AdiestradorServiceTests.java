package org.springframework.samples.petclinic.service;


import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdiestradorServiceTests {
	
	
	@Autowired
	protected AdiestradorService		adiestradorService;
	
	
	
	@Test
	public void checkFindByStar(){
		Collection<Adiestrador> adiestrador = this.adiestradorService.findAdiestradorByEstrellas(5);
		Assertions.assertThat(!adiestrador.isEmpty());
	}
	
	@Test
	public void checkFindAll(){
		Iterable<Adiestrador> adiestrador = this.adiestradorService.findAll();
		Assertions.assertThat(adiestrador != null);
	}

	
	@Test
	public void checkCount(){
		Integer adiestrador = this.adiestradorService.adiestradorCount();
		Assertions.assertThat(adiestrador != 0);
	}

}
