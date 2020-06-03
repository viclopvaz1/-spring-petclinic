package org.springframework.samples.petclinic.LoginUI;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUITest {
	
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

  @ParameterizedTest
  @CsvSource({"owner1","vet1","adiestrador1","admin1"})
  public void testLogin(String username) throws Exception {
	  
	  as(username).
	  whenIamLoggedIntheSystem().
	  thenISeeMyUsernameInTheMenuBar();
  }
  
  private LoginUITest as(final String username) {
	  this.username = username;
	  this.driver.get("http://localhost:" + this.port);
	  driver.findElement(By.linkText("LOGIN")).click();
	  driver.findElement(By.id("username")).clear();
	  driver.findElement(By.id("username")).sendKeys(username);
	  driver.findElement(By.id("password")).clear();
	  driver.findElement(By.id("password")).sendKeys(passwordOf(username));
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  return this;
	}
  
  private void thenISeeMyUsernameInTheMenuBar() {
	  assertEquals(this.username.toUpperCase(), driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
	
  }
  
  private CharSequence passwordOf(String username) {
	  if(username.contains("owner1")) {
		return "0wn3r";
	} else if(username.contains("vet1")) {
		return "v3t";
	} else if(username.contains("adiestrador1")) {
		return "adiestrador";
	}
	  return "4dm1n";
}
  private LoginUITest whenIamLoggedIntheSystem() {	
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

