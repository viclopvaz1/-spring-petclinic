package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adiestrador;
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
