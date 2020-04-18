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

public class PagarCitaOperacionUITest {
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
  public void testUntitledTestCase() throws Exception {
    driver.get("http://localhost:8080/");
	driver.findElement(By.linkText("LOGIN")).click();
	driver.findElement(By.id("username")).clear();
	driver.findElement(By.id("username")).sendKeys("owner1");
	driver.findElement(By.id("password")).clear();
	driver.findElement(By.id("password")).sendKeys("0wn3r");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	driver.findElement(By.linkText("VETERINARIANS")).click();
    assertEquals("300", driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[3]")).getText());
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.name("lastName")).click();
    driver.findElement(By.name("lastName")).clear();
    driver.findElement(By.name("lastName")).sendKeys("Franklin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("1200", driver.findElement(By.xpath("//tr[5]/td")).getText());
    driver.findElement(By.linkText("Citas Operaciones")).click();
    assertEquals("false", driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[8]")).getText());
    driver.findElement(By.linkText("Pagar")).click();
    assertEquals("true", driver.findElement(By.xpath("//table[@id='citasOperacionesPetsTable']/tbody/tr/td[8]")).getText());
    driver.findElement(By.linkText("FIND OWNERS")).click();
    driver.findElement(By.name("lastName")).click();
    driver.findElement(By.name("lastName")).clear();
    driver.findElement(By.name("lastName")).sendKeys("Franklin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("1150", driver.findElement(By.xpath("//tr[5]/td")).getText());
    driver.findElement(By.linkText("VETERINARIANS")).click();
    assertEquals("350", driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[3]")).getText());
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

