package org.springframework.samples.petclinic.service;



import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataAdiestradorRepository;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@DisplayName("A stack")
public class AdiestradorServiceTests {
	
	@Autowired
	protected AdiestradorService		adiestradorService;
	
	Adiestrador			adiestrador;//peta----------------------------------
//	
//    private static final int ESTRELLAS = 5;
//    
//    private static final int ID = 1;
// 
//    private static final String NOMBRE = "Aureliano";
// 
//    private static final String APELLIDO = "Buendía";
// 
//    private static final int TELEFONO = 625871852;
//    
//    private static final int MONEDERO = 100;

//
//	@Before
//	private void setUp() {
//		this.adiestrador = new Adiestrador();
//		adiestrador.setEstrellas(ESTRELLAS);
//		adiestrador.setFirstName(NOMBRE);
//		adiestrador.setId(ID);
//		adiestrador.setLastName(APELLIDO);
//		adiestrador.setMonedero(MONEDERO);
//		adiestrador.setTelefono(TELEFONO);
//	}
//	
//    @Test
//    public void testFromAdiestrador() {
//        List<Adiestrador> adiestrador = (List<Adiestrador>) adiestradorService.findAll();
//        Adiestrador adiestrador2 = adiestrador.get(1);
//        
//        Assert.assertNotNull(adiestrador2);
//        Assert.assertTrue(adiestrador2.getEstrellas() == ESTRELLAS);
//        Assert.assertEquals(adiestrador2.getFirstName(), NOMBRE);
//        Assert.assertEquals(adiestrador2.getLastName(), APELLIDO);
//        Assert.assertTrue(adiestrador2.getId() == ID);
//        Assert.assertTrue(adiestrador2.getMonedero() == MONEDERO);
//        Assert.assertTrue(adiestrador2.getTelefono() ==  TELEFONO);
//      
//    }
	
	@Test//POSITIVO
	void PositiveFindAdiestrador() {
		Iterable<Adiestrador> adiestrador = this.adiestradorService.findAll();
		Assertions.assertThat(adiestrador).isNotNull();
	}
	

	@Test//NEGATIVO
	void shouldNotFindAdiestrador() {
		SpringDataAdiestradorRepository SpringAdiestradorRepository = Mockito.mock(SpringDataAdiestradorRepository.class);
		this.adiestradorService = new AdiestradorService(SpringAdiestradorRepository);
		Mockito.when(SpringAdiestradorRepository.findAll()).thenReturn(new ArrayList<Adiestrador>());
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.adiestradorService.findAll();
		});
	}
	
	@Test//NEGATIVO
	void shouldNotFindAdiestradorEstrellas() {
		SpringDataAdiestradorRepository SpringAdiestradorRepository = Mockito.mock(SpringDataAdiestradorRepository.class);
		this.adiestradorService = new AdiestradorService(SpringAdiestradorRepository);
		Mockito.when(SpringAdiestradorRepository.findAdiestradorByEstrellas(5)).thenReturn(new ArrayList<Adiestrador>());
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.adiestradorService.findAdiestradorByEstrellas(5);
		});
	}
	
//	@ParameterizedTest // POSITIVO
//	@CsvSource({
//		"1", "2"
//	})
//	public void shouldFindAdiestradorEstrellas(final Integer estrellas) {
//		Collection<Adiestrador> adiestrador = this.adiestradorService.findAdiestradorByEstrellas(estrellas);
//		Assertions.assertThat(adiestrador).isNotNull();
//	}
//	
	
	@Test
	public void checkFindByStar(){
		Collection<Adiestrador> adiestrador = this.adiestradorService.findAdiestradorByEstrellas(5);
		Assertions.assertThat(adiestrador != null);
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
	
	@ParameterizedTest
	@CsvSource({//NEGATIVO 
		"adiestrador1", "adiestrador2"
	})
	public void testFindAdiestradorByUsernamesvSource(final String username) {
		Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
		Assertions.assertThat(adiestrador).isNotNull();
	}
	
	@ParameterizedTest
	@CsvSource({//NEGATIVO 
		"adiestrador1444", "adiestrador144"
	})
	public void testNotFindAdiestradorByUsermeCsvSource(final String username) {
		Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
		Assertions.assertThat(adiestrador).isNull();
	}
	

}
