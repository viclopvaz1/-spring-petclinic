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
public class BuscarPorTipoOperacionUITest {
	
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
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void listarTodasLasCitasOperaciones() throws Exception {
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenISeeAllCitasOperaciones();
  }
  
  @Test
  public void listarUnaCitaOperacion() throws Exception {
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenISeeOneCitasOperaciones();
  }
  
  @Test
  public void listarCitasOperacionesConUnTipoOperacionInvalidaOSinRegistros() throws Exception {
	  as("vet1").
	  whenIamLoggedIntheSystem().
	  thenIDontSeeAnyCitasOperaciones();
  }
  
  private BuscarPorTipoOperacionUITest as(final String vet) {
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
  
  private CharSequence passwordOf(String username) {
		return "v3t";
	}

private BuscarPorTipoOperacionUITest whenIamLoggedIntheSystem() {	
		return this;
	}

private void thenISeeAllCitasOperaciones() {
	driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[9]/a/span[2]")).click();
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	assertEquals("CitasOperaciones", driver.findElement(By.xpath("//h2")).getText());
}

private void thenISeeOneCitasOperaciones() {
	driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[9]/a/span[2]")).click();
	driver.findElement(By.id("tipoOperacion.name")).click();
	driver.findElement(By.id("tipoOperacion.name")).clear();
	driver.findElement(By.id("tipoOperacion.name")).sendKeys("Cirugia visual");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	assertEquals("Cita Operacion Information", driver.findElement(By.xpath("//h2")).getText());
}

private void thenIDontSeeAnyCitasOperaciones() {
	driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[9]/a/span[2]")).click();
	driver.findElement(By.id("tipoOperacion.name")).click();
	driver.findElement(By.id("tipoOperacion.name")).clear();
	driver.findElement(By.id("tipoOperacion.name")).sendKeys("hola");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	assertEquals("No hay ninguna cita de operacion con ese tipo o ha introducido un tipo de operacion inexistente", driver.findElement(By.xpath("//h2[2]")).getText());
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

