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

public class CrearCitaOperacionUITest {
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
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
    driver.findElement(By.linkText("FIND OWNERS")).click();
    driver.findElement(By.name("lastName")).click();
    driver.findElement(By.name("lastName")).clear();
    driver.findElement(By.name("lastName")).sendKeys("Franklin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Pedir Cita Operacion")).click();
    driver.findElement(By.id("fechaInicio")).click();
    driver.findElement(By.linkText("10")).click();
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

