package org.springframework.samples.petclinic.web.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class BuscarPorTipoOperacionUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver="C:\\Users\\vlope\\Downloads";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
	driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
	  driver.get("http://localhost:8080/");
	  driver.findElement(By.linkText("LOGIN")).click();
	  driver.findElement(By.id("username")).clear();
	  driver.findElement(By.id("username")).sendKeys("vet1");
	  driver.findElement(By.id("password")).clear();
	  driver.findElement(By.id("password")).sendKeys("v3t");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[9]/a/span[2]")).click();
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("CitasOperaciones", driver.findElement(By.xpath("//h2")).getText());
	  driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[9]/a/span[2]")).click();
	  driver.findElement(By.id("tipoOperacion.name")).click();
	  driver.findElement(By.id("tipoOperacion.name")).clear();
	  driver.findElement(By.id("tipoOperacion.name")).sendKeys("Cirugia visual");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  assertEquals("Cita Operacion Information", driver.findElement(By.xpath("//h2")).getText());
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

