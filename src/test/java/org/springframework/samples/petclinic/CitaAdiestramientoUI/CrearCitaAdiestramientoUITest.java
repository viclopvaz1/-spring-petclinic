package org.springframework.samples.petclinic.CitaAdiestramientoUI;


import static org.junit.Assert.*;

import org.junit.jupiter.api.extension.ExtendWith;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrearCitaAdiestramientoUITest {

	@LocalServerPort
	private int port;
	
  private String username;
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void crearCitaAdiestramientoSinErrores() throws Exception {
    
	  as("adiestrador1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaAdiestramiento();
  }
  @Test
  public void crearCitaAdiestramientoConErrores() throws Exception {
    
	  as("adiestrador1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaAdiestramientoWithErrors();
  }
  
  @Test
  public void crearCitaAdiestramientoConErroresEnFechaInicio() throws Exception {
    
	  as("adiestrador1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaAdiestramientoWithErrorInFechaInicio();
  
  }


private CrearCitaAdiestramientoUITest as(final String adiestrador) {
	this.username = adiestrador;
	  this.driver.get("http://localhost:" + this.port);
    driver.findElement(By.linkText("LOGIN")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("adiestrador1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("adiestrador");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    return this;
}
    
    
private CrearCitaAdiestramientoUITest thenIPressTheCreateButton() {
	  driver.findElement(By.linkText("FIND OWNERS")).click();
	  driver.findElement(By.name("lastName")).click();
	  driver.findElement(By.name("lastName")).clear();
	  driver.findElement(By.name("lastName")).sendKeys("Franklin");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  driver.findElement(By.linkText("Pedir cita Adiestramiento")).click();
	  return this;
}
private void andThenICreateCitaAdiestramiento() {
   
    driver.findElement(By.id("fechaInicio")).click();
    driver.findElement(By.linkText("16")).click();
    driver.findElement(By.id("hora")).click();
    driver.findElement(By.id("hora")).clear();
    driver.findElement(By.id("hora")).sendKeys("15:30");
    driver.findElement(By.id("duracion")).click();
    driver.findElement(By.id("duracion")).clear();
    driver.findElement(By.id("duracion")).sendKeys("30");
    driver.findElement(By.id("precio")).click();
    driver.findElement(By.id("precio")).clear();
    driver.findElement(By.id("precio")).sendKeys("50.0");
    new Select(driver.findElement(By.id("tipoAdiestramiento"))).selectByVisibleText("Adiestramiento deportivo");
    driver.findElement(By.xpath("//option[@value='Adiestramiento deportivo']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();


  }
private void andThenICreateCitaAdiestramientoWithErrors() {
	
    driver.findElement(By.id("fechaInicio")).click();
    driver.findElement(By.linkText("23")).click();
    driver.findElement(By.id("hora")).click();
    driver.findElement(By.id("hora")).clear();
    driver.findElement(By.id("hora")).sendKeys("15:30");
    driver.findElement(By.id("duracion")).click();
    driver.findElement(By.id("duracion")).clear();
    driver.findElement(By.id("duracion")).sendKeys("40");
    driver.findElement(By.id("precio")).click();
    driver.findElement(By.id("precio")).clear();
    driver.findElement(By.id("precio")).sendKeys("50.0er");
    new Select(driver.findElement(By.id("tipoAdiestramiento"))).selectByVisibleText("Adiestramiento deportivo");
    driver.findElement(By.xpath("//option[@value='Adiestramiento deportivo']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[4]/div")).click();
    assertEquals("Failed to convert property value of type java.lang.String to required type java.lang.Double for property precio; nested exception is java.lang.NumberFormatException: For input string: \"50.0er\"", driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[4]/div/span[2]")).getText());
    driver.findElement(By.xpath("//body/div")).click();
  }
	
private void andThenICreateCitaAdiestramientoWithErrorInFechaInicio() {
	driver.findElement(By.id("fechaInicio")).click();
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.id("hora")).click();
    driver.findElement(By.id("hora")).clear();
    driver.findElement(By.id("hora")).sendKeys("15:30");
    driver.findElement(By.id("duracion")).click();
    driver.findElement(By.id("duracion")).clear();
    driver.findElement(By.id("duracion")).sendKeys("30");
    driver.findElement(By.id("precio")).click();
    driver.findElement(By.id("precio")).clear();
    driver.findElement(By.id("precio")).sendKeys("50.0");
    new Select(driver.findElement(By.id("tipoAdiestramiento"))).selectByVisibleText("Adiestramiento deportivo");
    driver.findElement(By.xpath("//option[@value='Adiestramiento deportivo']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//body/div")).click();
    assertEquals("La fecha de inicio debe ser mayor a la fecha actual.", driver.findElement(By.xpath("//b")).getText());
  }

private CharSequence passwordOf(String username) {
		return "adiestrador";
	}


private CrearCitaAdiestramientoUITest whenIamLoggedIntheSystem() {
	
	return this;
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

