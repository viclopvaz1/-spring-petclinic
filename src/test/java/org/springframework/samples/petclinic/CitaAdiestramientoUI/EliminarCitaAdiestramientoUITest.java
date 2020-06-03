package org.springframework.samples.petclinic.CitaAdiestramientoUI;

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
public class EliminarCitaAdiestramientoUITest {

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

	@Test
	public void testUntitledTestCase() throws Exception {
		as("adiestrador1").whenIamLoggedIntheSystem().thenIDeleteCitaAdiestramiento();
	}

	private EliminarCitaAdiestramientoUITest as(final String adiestrador) {
		this.username = adiestrador;
		this.driver.get("http://localhost:" + this.port);
		driver.findElement(By.linkText("LOGIN")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("adiestrador1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("adiestrador");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private CharSequence passwordOf(String username) {
		return "adiestrador";
	}

	private EliminarCitaAdiestramientoUITest whenIamLoggedIntheSystem() {
		return this;
	}

	private void thenIDeleteCitaAdiestramiento() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[11]/a/span[2]")).click();
		driver.findElement(By.linkText("Leo")).click();
		driver.findElement(By.linkText("Delete")).click();
		assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
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
