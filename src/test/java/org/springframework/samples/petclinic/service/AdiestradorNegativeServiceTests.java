package org.springframework.samples.petclinic.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.*;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;

public class AdiestradorNegativeServiceTests {	  
	
	@Autowired
	protected AdiestradorService		adiestradorService;
	
	Adiestrador			adiestrador;
	
	@BeforeEach
	private void setUp() {
		this.adiestrador = new Adiestrador();
		adiestrador.setEstrellas(5);
		adiestrador.setFirstName("Alberto");
		adiestrador.setId(1);
		adiestrador.setLastName("Cordón");
		adiestrador.setMonedero(100);
		adiestrador.setTelefono(625874512);
			User usuario =new User();
			usuario.setUsername("Sam");
			usuario.setPassword("supersecretpassword");
			usuario.setEnabled(true);
		adiestrador.setUser(usuario);
			Set<CitaAdiestramiento> citas =  new HashSet<>();
			CitaAdiestramiento cita = new CitaAdiestramiento();
			cita.setDuracion(22);
			cita.setId(2);
			cita.setPrecio(122.0);
			cita.setPagado(true);
			citas.add(cita);
			
			CitaAdiestramiento cita2 = new CitaAdiestramiento();
			cita2.setDuracion(22);
			cita2.setId(2);
			cita2.setPrecio(122.0);
			cita2.setPagado(true);
			citas.add(cita2);
		adiestrador.setCitasAdiestramiento(citas);
			PetType petTipe = new PetType();
			petTipe.setId(1);
			petTipe.setName("perro");
		adiestrador.setTipoAnimal(petTipe);
	}
	

	//** NumberFormatException && IllegalArgumentException
	//++ NullPointerException
	//.. IndexOutOfBoundsException
	//,, ArithmeticException
	
	
	//--------------telefono-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionTelefono() { //**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	adiestrador.setTelefono(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionTelefonoNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setTelefono(null);
	    	adiestrador.getTelefono().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionTelefonoIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getTelefono().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionTelefonoArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = adiestrador.getTelefono() / 0;
	    });
	  }
	
	//--------------tipoAnimal-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionTipoAnimalNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setTipoAnimal(null);
	    	adiestrador.getTipoAnimal().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionTipoAnimalIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getTipoAnimal().getName().toString().charAt(15454);
	    });
	  }
	

	//--------------lastname-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionLastNameNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setLastName(null);
	    	adiestrador.getLastName().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionLastNameIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getLastName().charAt(234);
	    });
	  }
	
	//--------------firstName-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionFirstNameNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setFirstName(null);
	    	adiestrador.getFirstName().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionFirstNameIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getFirstName().charAt(234);
	    });
	  }
	//--------------user-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionUserNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setUser(null);
	    	adiestrador.getUser().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionUserIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getUser().getUsername().toString().charAt(15454);
	    });
	  }
	//--------------estrellas-----------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionEstrellas() {//**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	adiestrador.setEstrellas(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionEstrellasNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setEstrellas(null);
	    	adiestrador.getEstrellas().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionEstrellasIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getEstrellas().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionEstrellasArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = adiestrador.getEstrellas() / 0;
	    });
	  }
	//--------------citasAdiestramiento-------------------------------------------------------------------------
	@Test
	void testExpectedExceptionIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	Object[] array = adiestrador.getCitasAdiestramiento().toArray();
	    	CitaAdiestramiento cita = (CitaAdiestramiento) array[2];
	    });
	  }
	
	@Test
	  void testExpectedExceptionCitasNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setCitasAdiestramiento(null);
	    	adiestrador.getCitasAdiestramiento().toString();
	    });
	  }

	//--------------id-----------------------------------------------------------------------------------------
	@Test
	  void testExpectedExceptionId() {//**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	adiestrador.setId(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setId(null);
	    	adiestrador.getId().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getId().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = adiestrador.getId() / 0;
	    });
	  }
	
	//--------------monedero--------------
	@Test
	  void testExpectedExceptionMonedero() {//**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	adiestrador.setMonedero(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionMonederoNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	adiestrador.setMonedero(null);
	    	adiestrador.getMonedero().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionMonederoIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	adiestrador.getMonedero().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionMonederoArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = adiestrador.getMonedero() / 0;
	    });
	  }

}
