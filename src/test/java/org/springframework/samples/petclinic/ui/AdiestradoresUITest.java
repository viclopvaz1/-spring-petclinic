package org.springframework.samples.petclinic.ui;


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
import org.openqa.selenium.support.ui.Select;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdiestradoresUITest {
	
	@LocalServerPort
	private int port;
	
	  private WebDriver driver;
	  private String baseUrl;
	  private String username;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeEach
	  public void setUp() throws Exception {
//		String lugar = "C:\\Users\\House\\webdrivers";
//		System.setProperty("webdriver.chrome.driver", lugar + "\\chromedriver.exe");
	    driver = new FirefoxDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }
	  
	  @Test
	  public void listarAdiestradores() throws Exception {
		  as("vet1").
		  whenIamLoggedIntheSystem().
		  thenISeeAdiestradores();
	  }
	  
	  private AdiestradoresUITest as(final String vet) {
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
	  
	  private AdiestradoresUITest whenIamLoggedIntheSystem() {	
			return this;
		}
	  
	  
	  
	  
	  

	  public void thenISeeAdiestradores() {
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
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




