package org.springframework.samples.petclinic.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.User;

public class CausasNegativeServiceTests {	
	
	Causa			causa;
	
	@BeforeEach
	private void setUp() {
		this.causa = new Causa();
		causa.setDineroRecaudado(12000);
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now();
		causa.setFechaFin(a);
		causa.setFechaInicio(b);
		causa.setId(1);
		causa.setObjetivo(1222);
		causa.setOng("sdg");
		causa.setValido(true);
		
		Donacion don = new Donacion();
		don.setCantidad(1200);
		don.setId(1);
		List<Donacion> ls = new ArrayList<>();
		ls.add(don);

			User usuario =new User();
			usuario.setUsername("Sam");
			usuario.setPassword("supersecretpassword");
			usuario.setEnabled(true);

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
			
			PetType petTipe = new PetType();
			petTipe.setId(1);
			petTipe.setName("perro");
		causa.setDonaciones(ls);

	}
	
	//** NumberFormatException && IllegalArgumentException
	//++ NullPointerException
	//.. IndexOutOfBoundsException
	//,, ArithmeticException
	
	//------------------------------fechaInicio--------------------------
	@Test
	  void testExpectedExceptionFechaInicioNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setFechaInicio(null);
	    	causa.getFechaInicio().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionFechaInicioIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getFechaInicio().toString().charAt(234);
	    });
	  }
	
	//------------------------------fechaFin--------------------------
	@Test
	  void testExpectedExceptionFechaFinNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setFechaFin(null);
	    	causa.getFechaFin().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionFechaFinIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getFechaFin().toString().charAt(234);
	    });
	  }
	
	//------------------------------ong--------------------------
	
	@Test
	  void testExpectedExceptionOngNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setOng(null);
	    	causa.getOng().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionOngIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getOng().charAt(234);
	    });
	  }
	
	//------------------------------objetivo--------------------------
	@Test
	  void testExpectedExceptionObjetivo() { //**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	causa.setObjetivo(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionObjetivoNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setObjetivo(null);
	    	causa.getObjetivo().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionObjetivoIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getObjetivo().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionObjetivoArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = causa.getObjetivo() / 0;
	    });
	  }
	
	//------------------------------dineroRecaudado--------------------------
	@Test
	  void testExpectedExceptionDineroRecaudado() { //**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	causa.setDineroRecaudado(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionDineroRecaudadoNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setDineroRecaudado(null);
	    	causa.getDineroRecaudado().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionDineroRecaudadoIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getDineroRecaudado().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionDineroRecaudadoArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = causa.getDineroRecaudado() / 0;
	    });
	  }
	
	//------------------------------valido--------------------------
	@Test
	  void testExpectedExceptionValidoNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	Boolean a = null;
	    	causa.setValido(a);
	    });
	  }
	
	
	
	//------------------------------donaciones--------------------------
	
	@Test
	void testExpectedExceptionIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getDonaciones().get(544);
	    });
	  }
	
	@Test
	  void testExpectedExceptionDonacionesNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setDonaciones(null);
	    	causa.getDonaciones().toString();
	    });
	  }
	
	//------------------------------id--------------------------
	
	@Test
	  void testExpectedExceptionId() { //**************************************************************
	 
	    Assertions.assertThrows(NumberFormatException.class, () -> {
	    	Integer numeroMalo = Integer.parseInt("Uno");
	    	causa.setId(numeroMalo);
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdNull() {//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	    Assertions.assertThrows(NullPointerException.class, () -> {
	    	causa.setId(null);
	    	causa.getId().toString();
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdIndex() {//.................................................................
	 
	    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
	    	causa.getId().toString().charAt(15454);
	    });
	  }
	
	@Test
	  void testExpectedExceptionIdArithmetic() {//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	 
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	Integer a = causa.getId() / 0;
	    });
	  }
	
	//----------------------------------------------------------------------------------------------------------
	
	


}
