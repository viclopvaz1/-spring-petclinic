package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
		.thenAddMoney();
	}
	
	@Test
	public void DonacionNegativa() throws Exception {
		as("vet1")
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
		return "v3t";
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

}
