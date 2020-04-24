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
//		String lugar = "C:\\Users\\House\\webdrivers";
//		System.setProperty("webdriver.chrome.driver", lugar + "\\chromedriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void Donacion() throws Exception {
		as("vet1")
		.whenIamLoggedIntheSystem()
		.thenDonacion();
	}

	@Test
	public void dineroSuperiorAMonedero() throws Exception {
		as("vet1")
		.whenIamLoggedIntheSystem()
		.thenDonacionSuperiorMonedero();
	}

	@Test
	public void dineroErroneo() throws Exception {
		as("vet1")
		.whenIamLoggedIntheSystem()
		.thenDonacionErronea();
	}

	@Test
	public void dineroNull() throws Exception {
		as("vet1")
		.whenIamLoggedIntheSystem()
		.	thenDonacionNull();
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
		return "v3t";
	}

	private DonacionesUITest whenIamLoggedIntheSystem() {
		return this;
	}

	// -----------------------------------------------

	public void thenDonacion() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.linkText("Mi mascota")).click();
		driver.findElement(By.linkText("Hacer Donacion")).click();
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public void thenDonacionSuperiorMonedero() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.linkText("Mi mascota")).click();
		driver.findElement(By.linkText("Hacer Donacion")).click();
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("10000");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Dinero superior a su monedero", driver.findElement(By.xpath("//b")).getText());
	}

	public void thenDonacionErronea() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.linkText("Mi super mascota")).click();
		driver.findElement(By.linkText("Hacer Donacion")).click();
		driver.findElement(By.id("cantidad")).click();
		driver.findElement(By.id("cantidad")).clear();
		driver.findElement(By.id("cantidad")).sendKeys("0");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='add-donacion-form']/div/div/div/span[2]")).getText());
	}
	
	  public void thenDonacionNull() throws Exception {
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
	    driver.findElement(By.linkText("Mi super mascota")).click();
	    driver.findElement(By.linkText("Hacer Donacion")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-donacion-form']/div/div/div/span[2]")).getText());
	  }

}
