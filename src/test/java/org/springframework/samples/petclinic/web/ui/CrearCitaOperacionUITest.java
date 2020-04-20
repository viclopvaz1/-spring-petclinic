package org.springframework.samples.petclinic.web.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
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
public class CrearCitaOperacionUITest {
	
	@LocalServerPort
	private int port;
	
  private String username;
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
//	String pathToGeckoDriver="C:\\Users\\vlope\\Downloads";
//	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
	driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void crearCitaOperacionSinErrores() throws Exception {
    
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaOperacion();
  }
  
  @Test
  public void crearCitaOperacionConErrores() throws Exception {
    
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaOperacionWithErrors();
  }
  
  @Test
  public void crearCitaOperacionConErroresEnFechaInicio() throws Exception {
    
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenIPressTheCreateButton().
	  andThenICreateCitaOperacionWithErrorInFechaInicio();
  }
  
  private CrearCitaOperacionUITest as(final String vet) {
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
  
  private CrearCitaOperacionUITest thenIPressTheCreateButton() {
	  driver.findElement(By.linkText("FIND OWNERS")).click();
	  driver.findElement(By.name("lastName")).click();
	  driver.findElement(By.name("lastName")).clear();
	  driver.findElement(By.name("lastName")).sendKeys("Franklin");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  driver.findElement(By.linkText("Pedir Cita Operacion")).click();
	  return this;
  }
  
  private void andThenICreateCitaOperacion() {
	  driver.findElement(By.id("fechaInicio")).click();
	  driver.findElement(By.id("fechaInicio")).clear();
	  driver.findElement(By.id("fechaInicio")).sendKeys("2020/12/17");
	  driver.findElement(By.id("hora")).click();
	  driver.findElement(By.id("hora")).clear();
	  driver.findElement(By.id("hora")).sendKeys("15:00");
	  driver.findElement(By.id("duracion")).click();
	  driver.findElement(By.id("duracion")).clear();
	  driver.findElement(By.id("duracion")).sendKeys("30");
	  driver.findElement(By.id("precio")).click();
	  driver.findElement(By.id("precio")).clear();
	  driver.findElement(By.id("precio")).sendKeys("100");
	  new Select(driver.findElement(By.id("tipoOperacion"))).selectByVisibleText("Cirugia basica");
	  driver.findElement(By.xpath("//option[@value='Cirugia basica']")).click();
	  driver.findElement(By.id("cantidadPersonal")).click();
	  driver.findElement(By.id("cantidadPersonal")).clear();
	  driver.findElement(By.id("cantidadPersonal")).sendKeys("2");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("Cita Operacion Information", driver.findElement(By.xpath("//h2")).getText());
  }
  
  private void andThenICreateCitaOperacionWithErrors() {
	  driver.findElement(By.id("fechaInicio")).click();
	  driver.findElement(By.id("hora")).click();
	  driver.findElement(By.id("duracion")).click();
	  driver.findElement(By.id("precio")).click();
	  driver.findElement(By.id("cantidadPersonal")).click();
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div/div/span[2]")).getText());
	  assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[2]/div/span[2]")).getText());
	  assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[3]/div/span[2]")).getText());
	  assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[4]/div/span[2]")).getText());
	  assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[6]/div/span[2]")).getText());
	  driver.findElement(By.id("duracion")).click();
	  driver.findElement(By.id("duracion")).clear();
	  driver.findElement(By.id("duracion")).sendKeys("-100");
	  driver.findElement(By.id("precio")).click();
	  driver.findElement(By.id("precio")).clear();
	  driver.findElement(By.id("precio")).sendKeys("-100");
	  driver.findElement(By.id("cantidadPersonal")).click();
	  driver.findElement(By.id("cantidadPersonal")).clear();
	  driver.findElement(By.id("cantidadPersonal")).sendKeys("-52");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[3]/div/span[2]")).getText());
	  assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[4]/div/span[2]")).getText());
	  assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='add-citaOperacion-form']/div/div[6]/div/span[2]")).getText());
  }
  
  private void andThenICreateCitaOperacionWithErrorInFechaInicio() {
	  driver.findElement(By.id("fechaInicio")).click();
	  driver.findElement(By.id("fechaInicio")).clear();
	  driver.findElement(By.id("fechaInicio")).sendKeys("2020/03/27");
	  driver.findElement(By.id("hora")).click();
	  driver.findElement(By.id("hora")).clear();
	  driver.findElement(By.id("hora")).sendKeys("15:00");
	  driver.findElement(By.id("duracion")).click();
	  driver.findElement(By.id("duracion")).clear();
	  driver.findElement(By.id("duracion")).sendKeys("30");
	  driver.findElement(By.id("precio")).click();
	  driver.findElement(By.id("precio")).clear();
	  driver.findElement(By.id("precio")).sendKeys("100");
	  new Select(driver.findElement(By.id("tipoOperacion"))).selectByVisibleText("Cirugia basica");
	  driver.findElement(By.xpath("//option[@value='Cirugia basica']")).click();
	  driver.findElement(By.id("cantidadPersonal")).click();
	  driver.findElement(By.id("cantidadPersonal")).clear();
	  driver.findElement(By.id("cantidadPersonal")).sendKeys("2");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("La fecha de inicio debe ser mayor a la fecha actual.", driver.findElement(By.xpath("//b")).getText());
  }
  
  private CharSequence passwordOf(String username) {
		return "v3t";
	}
  
  private CrearCitaOperacionUITest whenIamLoggedIntheSystem() {	
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

