package org.springframework.samples.petclinic.CausaDonacionUI;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
public class DonacionesUITest {

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@ParameterizedTest
	@CsvSource({"owner7","vet1","adiestrador2"})
	public void Donacion(String username) throws Exception {
		as(username)
		.whenIamLoggedIntheSystem()
		.thenPressDonationButton()
		.thenDonacion();
	}

	@ParameterizedTest
	@CsvSource({"owner7","vet1","adiestrador2"})
	public void dineroSuperiorAMonedero(String username) throws Exception {
		as(username)
		.whenIamLoggedIntheSystem()
		.thenPressDonationButton()
		.thenDonacionSuperiorMonedero();
	}

	@ParameterizedTest
	@CsvSource({"owner7","vet1","adiestrador2"})
	public void dineroErroneo(String username) throws Exception {
		as(username)
		.whenIamLoggedIntheSystem()
		.thenPressDonationButton()
		.thenDonacionErronea();
	}

	@ParameterizedTest
	@CsvSource({"owner7","vet1","adiestrador2"})
	public void dineroNull(String username) throws Exception {
		as(username)
		.whenIamLoggedIntheSystem()
		.thenPressDonationButton()
		.thenDonacionNull();
	}

	private DonacionesUITest as(final String vet) {
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
		if(username.contains("owner7")) {
			return "0wn3r";
		} else if(username.contains("vet1")) {
			return "v3t";
		}
			return "adiestrador";
	}

	private DonacionesUITest whenIamLoggedIntheSystem() {
		return this;
	}

	// -----------------------------------------------
	
	public DonacionesUITest thenPressDonationButton() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.linkText("Mi mascota")).click();
		driver.findElement(By.linkText("Hacer Donacion")).click();
		return this;
	}

	public void thenDonacion() throws Exception {
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public void thenDonacionSuperiorMonedero() throws Exception {
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("10000");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Dinero superior a su monedero", driver.findElement(By.xpath("//b")).getText());
	}

	public void thenDonacionErronea() throws Exception {
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("0");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='add-donacion-form']/div/div/div/span[2]")).getText());
	}
	
	  public void thenDonacionNull() throws Exception {
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-donacion-form']/div/div/div/span[2]")).getText());
	  }
	  
	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }


}
