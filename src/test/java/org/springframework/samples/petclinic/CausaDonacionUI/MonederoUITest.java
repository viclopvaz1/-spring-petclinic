package org.springframework.samples.petclinic.CausaDonacionUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MonederoUITest {

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
		.thenAddMoney();
	}
	
	@ParameterizedTest
	@CsvSource({"owner7","vet1","adiestrador2"})
	public void DonacionNegativa(String username) throws Exception {
		as(username)
		.whenIamLoggedIntheSystem()
		.thenNegativeMoney();
	}

	private MonederoUITest as(final String vet) {
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

	private MonederoUITest whenIamLoggedIntheSystem() {
		return this;
	}

	// -----------------------------------------

	public void thenAddMoney() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.linkText("Monedero")).click();
		driver.findElement(By.id("monedero")).click();
		driver.findElement(By.id("monedero")).clear();
		driver.findElement(By.id("monedero")).sendKeys("10");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public void thenNegativeMoney() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.linkText("Monedero")).click();
		driver.findElement(By.id("monedero")).click();
		driver.findElement(By.id("monedero")).clear();
		driver.findElement(By.id("monedero")).sendKeys("-54");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("tiene que ser mayor o igual que 0", driver.findElement(By.xpath("//form[@id='add-person-form']/div[2]/div/div/span[2]")).getText());
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
