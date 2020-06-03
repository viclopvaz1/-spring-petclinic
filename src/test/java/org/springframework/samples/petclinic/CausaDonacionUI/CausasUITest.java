package org.springframework.samples.petclinic.CausaDonacionUI;


import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CausasUITest {
	
	
	@LocalServerPort
    private int port;
	  private WebDriver driver;
	  private String baseUrl;
	  private String username;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();
	
	

	  @BeforeEach
	  public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	  }
	  
	  @Test
	  public void testCausasEnLasQueHeDonado() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenISeeCausasEnLasQueHeDonado();
	  }
	  
	  @Test
	  public void testEdicionCausas() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenEdicionCausas();
	  }
	  
	  @Test
	  public void testCreacionYBorrarCausas() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenCreacionYBorrarCausa();
	  }
	  
	  @Test
	  public void testErrorEdicion() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenErrorEdicion();
	  }
	  
	  @Test
	  public void testListadoCausasSugeridas() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenListadoCausasSugeridas();
	  }
	  
	  @Test
	  public void testSugerirCausa() throws Exception {
		  as2("owner1").
		  whenIamLoggedIntheSystem().
		  thenSugerirCausa();
	  }
	  
	  @Test
	  public void testSugerirCausaFechaIncorrecta() throws Exception {
		  as2("owner1").
		  whenIamLoggedIntheSystem().
		  thenSugerirCausaFechaIncorrecta();
	  }
	  
	  @Test
	  public void testSugerirCausaObjetivoIncorrecto() throws Exception {
		  as2("owner1").
		  whenIamLoggedIntheSystem().
		  thenSugerirCausaObjetivoIncorrecto();
	  }
	  
	  @Test
	  public void testBorrarCausasYDevolverDinero() throws Exception {
		  as("vet4").
		  whenIamLoggedIntheSystem().
		  thenCrearCausa().
		  thenPressDonationButton().
		  thenDonacion().
		  logout().
		  as2("owner6").
		  whenIamLoggedIntheSystem().
		  thenPressDonationButton().
		  thenDonacion().
		  logout().
		  as("vet4").
		  whenIamLoggedIntheSystem().
		  thenBorroCausa().
		  thenComprueboDevolucion();
	  }
	  

	  private CausasUITest as(final String vet) {
		  this.username = vet;
		  this.driver.get("http://localhost:" + this.port);
		  driver.findElement(By.linkText("LOGIN")).click();
		  driver.findElement(By.id("username")).clear();
		  driver.findElement(By.id("username")).sendKeys(username);
		  driver.findElement(By.id("password")).clear();
		  driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		  driver.findElement(By.xpath("//button[@type='submit']")).click();
		  return this;
		}
	  
	  private CausasUITest as2(final String owner) {
		  this.username = owner;
		  this.driver.get("http://localhost:" + this.port);
		  driver.findElement(By.linkText("LOGIN")).click();
		  driver.findElement(By.id("username")).clear();
		  driver.findElement(By.id("username")).sendKeys(username);
		  driver.findElement(By.id("password")).clear();
		  driver.findElement(By.id("password")).sendKeys(passwordOf2(username));
		  driver.findElement(By.xpath("//button[@type='submit']")).click();
		  return this;
		}
	  
	  public CausasUITest thenPressDonationButton() {
			driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
			driver.findElement(By.linkText("Pirola")).click();
			driver.findElement(By.linkText("Hacer Donacion")).click();
			return this;
		}

		public CausasUITest thenDonacion() throws Exception {
			driver.findElement(By.id("cantidad")).click();
			driver.findElement(By.id("cantidad")).clear();
			driver.findElement(By.id("cantidad")).sendKeys("2");
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			return this;
		}
		private CausasUITest logout() {
			  driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		      driver.findElement(By.linkText("Logout")).click();
		      driver.findElement(By.xpath("//button[@type='submit']")).click();
			  return this;
			}
	  
	  private CharSequence passwordOf(String username) {
			return "v3t";
		}
	  
	  private CharSequence passwordOf2(String username) {
			return "0wn3r";
		}
	  
	  private CausasUITest whenIamLoggedIntheSystem() {	
			return this;
		}
	  
	  
	  //------------------------------------------------------
	  
	  public CausasUITest thenCrearCausa() throws Exception {
		    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[8]/a/span[2]")).click();
		    driver.findElement(By.id("fechaInicio")).click();
		    driver.findElement(By.id("fechaInicio")).clear();
		    driver.findElement(By.id("fechaInicio")).sendKeys("2020/05/05");
		    driver.findElement(By.id("fechaFin")).clear();
		    driver.findElement(By.id("fechaFin")).sendKeys("2020/05/20");
		    driver.findElement(By.id("ong")).clear();
		    driver.findElement(By.id("ong")).sendKeys("Pirola");
		    driver.findElement(By.id("objetivo")).click();
		    driver.findElement(By.id("objetivo")).clear();
		    driver.findElement(By.id("objetivo")).sendKeys("1000");
		    driver.findElement(By.id("dineroRecaudado")).clear();
		    driver.findElement(By.id("dineroRecaudado")).sendKeys("0");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    return this;
		  }
	  
	  public CausasUITest thenBorroCausa() throws Exception {
		    driver.findElement(By.linkText("CAUSAS")).click();
		    driver.findElement(By.linkText("Pirola")).click();
		    driver.findElement(By.linkText("Delete")).click();
		    return this;
		  }
	  
	  public void thenComprueboDevolucion() throws Exception {
		    driver.findElement(By.linkText("VETERINARIANS")).click();
		    assertEquals("300", driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[3]")).getText());
	  	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		    driver.findElement(By.name("lastName")).click();
		    driver.findElement(By.name("lastName")).clear();
		    driver.findElement(By.name("lastName")).sendKeys("Coleman");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals("1200", driver.findElement(By.xpath("//tr[5]/td")).getText());
		  }
	  

	  public void thenISeeCausasEnLasQueHeDonado(){
	    driver.findElement(By.linkText("CAUSAS EN LAS QUE HE DONADO")).click();
	    driver.findElement(By.linkText("Mi mascota")).click();
	  }
	  
	  
	  
	  	  
	  public void thenEdicionCausas() {
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
	    driver.findElement(By.linkText("Mi mascota")).click();
	    driver.findElement(By.linkText("Edit")).click();
	    driver.findElement(By.id("dineroRecaudado")).click();
	    driver.findElement(By.id("dineroRecaudado")).clear();
	    driver.findElement(By.id("dineroRecaudado")).sendKeys("200");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("200", driver.findElement(By.xpath("//tr[5]/td")).getText());
	  }
	  


	  public void thenCreacionYBorrarCausa() throws Exception {
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[8]/a/span[2]")).click();
	    driver.findElement(By.id("fechaInicio")).click();
	    driver.findElement(By.id("fechaInicio")).clear();
	    driver.findElement(By.id("fechaInicio")).sendKeys("2020/05/05");
	    driver.findElement(By.id("fechaFin")).clear();
	    driver.findElement(By.id("fechaFin")).sendKeys("2020/05/20");
	    driver.findElement(By.id("ong")).clear();
	    driver.findElement(By.id("ong")).sendKeys("Canitas");
	    driver.findElement(By.id("objetivo")).click();
	    driver.findElement(By.id("objetivo")).clear();
	    driver.findElement(By.id("objetivo")).sendKeys("1000");
	    driver.findElement(By.id("dineroRecaudado")).clear();
	    driver.findElement(By.id("dineroRecaudado")).sendKeys("0");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Delete")).click();
	  }
	  

	  
	  public void thenErrorEdicion() throws Exception {
	    driver.findElement(By.linkText("CAUSAS")).click();
	    driver.findElement(By.linkText("Mi mascota")).click();
	    driver.findElement(By.linkText("Edit")).click();
	    driver.findElement(By.id("fechaFin")).click();
	    driver.findElement(By.id("fechaFin")).clear();
	    driver.findElement(By.id("fechaFin")).sendKeys("2018/05/03");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("La fecha de incio debe ser anterior a la de fin.", driver.findElement(By.xpath("//b")).getText());
	    driver.findElement(By.id("fechaFin")).click();
	    driver.findElement(By.id("fechaFin")).clear();
	    driver.findElement(By.id("fechaFin")).sendKeys("2020/05/03");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  
	  public void thenListadoCausasSugeridas() throws Exception {
	    driver.findElement(By.linkText("LISTAS DE CAUSAS SUGERIDAS")).click();
	  }
	  
	  
	  public void thenSugerirCausa() throws Exception {
		    driver.findElement(By.linkText("CREAR UNA CAUSA")).click();
		    driver.findElement(By.id("fechaInicio")).click();
		    driver.findElement(By.id("fechaInicio")).clear();
		    driver.findElement(By.id("fechaInicio")).sendKeys("2020/02/02");
		    driver.findElement(By.id("fechaFin")).click();
		    driver.findElement(By.id("fechaFin")).clear();
		    driver.findElement(By.id("fechaFin")).sendKeys("2020/02/05");
		    driver.findElement(By.id("ong")).click();
		    driver.findElement(By.id("ong")).clear();
		    driver.findElement(By.id("ong")).sendKeys("Mi Mascota Lola");
		    driver.findElement(By.id("objetivo")).click();
		    driver.findElement(By.id("objetivo")).clear();
		    driver.findElement(By.id("objetivo")).sendKeys("100");
		    driver.findElement(By.id("dineroRecaudado")).click();
		    driver.findElement(By.id("dineroRecaudado")).clear();
		    driver.findElement(By.id("dineroRecaudado")).sendKeys("0");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		  }
	  
	  
	  public void thenSugerirCausaFechaIncorrecta() throws Exception {
		    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[8]/a/span[2]")).click();
		    driver.findElement(By.id("fechaInicio")).click();
		    driver.findElement(By.id("fechaInicio")).clear();
		    driver.findElement(By.id("fechaInicio")).sendKeys("2020/02/02");
		    driver.findElement(By.id("fechaFin")).clear();
		    driver.findElement(By.id("fechaFin")).sendKeys("2019/02/03");
		    driver.findElement(By.id("ong")).clear();
		    driver.findElement(By.id("ong")).sendKeys("Mi Loro");
		    driver.findElement(By.id("objetivo")).clear();
		    driver.findElement(By.id("objetivo")).sendKeys("1000");
		    driver.findElement(By.id("dineroRecaudado")).clear();
		    driver.findElement(By.id("dineroRecaudado")).sendKeys("0");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals("La fecha de incio debe ser anterior a la de fin.", driver.findElement(By.xpath("//b")).getText());
		  }
	  
	  public void thenSugerirCausaObjetivoIncorrecto() throws Exception {
		    driver.findElement(By.linkText("CREAR UNA CAUSA")).click();
		    driver.findElement(By.id("fechaInicio")).click();
		    driver.findElement(By.id("fechaInicio")).clear();
		    driver.findElement(By.id("fechaInicio")).sendKeys("2020/05/05");
		    driver.findElement(By.id("fechaFin")).clear();
		    driver.findElement(By.id("fechaFin")).sendKeys("2020/06/06");
		    driver.findElement(By.id("ong")).clear();
		    driver.findElement(By.id("ong")).sendKeys("Mi Perrita Carmen");
		    driver.findElement(By.id("objetivo")).clear();
		    driver.findElement(By.id("objetivo")).sendKeys("100");
		    driver.findElement(By.id("dineroRecaudado")).clear();
		    driver.findElement(By.id("dineroRecaudado")).sendKeys("300");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals("El dinero recaudado debe ser menor al objetivo", driver.findElement(By.xpath("//b")).getText());
		  }
	 

	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }
	  
	  
	  

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }

}
