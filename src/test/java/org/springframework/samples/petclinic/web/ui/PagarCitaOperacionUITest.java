package org.springframework.samples.petclinic.web.ui;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PagarCitaOperacionUITest {
	
	@LocalServerPort
	private int port;
	
  private String monederoVet;
  private String monederoOwner;
  private String precioCitaOperacion;
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
  public void pagarCitaOperacion() throws Exception {
	
	  as("owner1").
	  whenIamLoggedIntheSystem().
	  thenIPayCitaOperacion();
  }
  
  @Test
  public void pagarCitaOperacionConDineroInsuficiente() throws Exception {
	
	  as("owner2").
	  whenIamLoggedIntheSystem().
	  thenIPayCitaOperacionWithLessMoney();
  }
  
  private PagarCitaOperacionUITest as(final String owner) {
	  this.username = owner;
	  this.driver.get("http://localhost:" + this.port);
	  driver.findElement(By.linkText("LOGIN")).click();
	  driver.findElement(By.id("username")).clear();
	  driver.findElement(By.id("username")).sendKeys(username);
	  driver.findElement(By.id("password")).clear();
	  driver.findElement(By.id("password")).sendKeys(passwordOf(username));
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  return this;
	}
  
  private void thenIPayCitaOperacion() {
	  driver.findElement(By.linkText("VETERINARIANS")).click();
	  this.monederoVet = driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[3]")).getText();
	  assertEquals("300", monederoVet);
	  driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
	  driver.findElement(By.name("lastName")).click();
	  driver.findElement(By.name("lastName")).clear();
	  driver.findElement(By.name("lastName")).sendKeys("Franklin");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  this.monederoOwner = driver.findElement(By.xpath("//tr[5]/td")).getText();
	  assertEquals("1200", monederoOwner);
	  driver.findElement(By.linkText("Citas Operaciones")).click();
	  this.precioCitaOperacion = driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[4]")).getText();
	  assertEquals("false", driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[8]")).getText());
	  driver.findElement(By.linkText("Pagar")).click();
	  assertEquals("true", driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[8]")).getText());
	  driver.findElement(By.linkText("FIND OWNERS")).click();
	  driver.findElement(By.name("lastName")).click();
	  driver.findElement(By.name("lastName")).clear();
	  driver.findElement(By.name("lastName")).sendKeys("Franklin");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals(this.pagoOwner(), driver.findElement(By.xpath("//tr[5]/td")).getText());
	  driver.findElement(By.linkText("VETERINARIANS")).click();
	  assertEquals(this.pagoVet(), driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[3]")).getText());
	
  }
  
  private void thenIPayCitaOperacionWithLessMoney() {
	  driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
  	  driver.findElement(By.name("lastName")).click();
  	  driver.findElement(By.name("lastName")).clear();
  	  driver.findElement(By.name("lastName")).sendKeys("Rodriquez");
  	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("10", driver.findElement(By.xpath("//tr[5]/td")).getText());
	  driver.findElement(By.linkText("Citas Operaciones")).click();
	  assertEquals("false", driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[8]")).getText());
	  driver.findElement(By.linkText("Pagar")).click();
	  assertEquals("No puedes pagar la cita si no tienes suficiente dinero en el monedero", driver.findElement(By.xpath("//h2[2]")).getText());
	
  }
  
  private CharSequence passwordOf(String username) {
		return "0wn3r";
	}
  
  private PagarCitaOperacionUITest whenIamLoggedIntheSystem() {	
		return this;
	}
  
  private String pagoOwner() {
	  String newPrecio = this.precioCitaOperacion.substring(0, this.precioCitaOperacion.length() - 2);
	  Integer newMonederoOwner = Integer.parseInt(this.monederoOwner) - Integer.parseInt(newPrecio);
	  return newMonederoOwner.toString();
  }
  
  private String pagoVet() {
	  String newPrecio = this.precioCitaOperacion.substring(0, this.precioCitaOperacion.length() - 2);
	  Integer newMonederoVet = Integer.parseInt(this.monederoVet) + Integer.parseInt(newPrecio);
	  return newMonederoVet.toString();
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

