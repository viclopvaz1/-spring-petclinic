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
public class PagarCitaAdiestramientoUITest {
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
	public void pagarCitaAdiestramiento() throws Exception {

		as("owner1").whenIamLoggedIntheSystem().thenIPayCitaAdiestramiento();
	}

	private PagarCitaAdiestramientoUITest whenIamLoggedIntheSystem() {
		return this;
	}

	@Test
	public void pagarCitaAdiestramientoSinDinero() throws Exception {

		as("owner2").whenIamLoggedIntheSystem().thenIPayCitaAdiestramientoWithOutMoney();
	}

	private void thenIPayCitaAdiestramientoWithOutMoney() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.name("lastName")).click();
		driver.findElement(By.name("lastName")).clear();
		driver.findElement(By.name("lastName")).sendKeys("Rodriquez");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("10", driver.findElement(By.xpath("//tr[5]/td")).getText());
		driver.findElement(By.linkText("Citas Adiestramiento")).click();
		assertEquals("false", driver
				.findElement(By.xpath("//table[@id='citasAdiestramientoOwnersIdTable']/tbody/tr/td[8]")).getText());
		driver.findElement(By.linkText("Pagar")).click();
		assertEquals("No puedes pagar la cita si no tienes suficiente dinero en el monedero",
				driver.findElement(By.xpath("//h2[2]")).getText());

	}

	private PagarCitaAdiestramientoUITest as(final String owner) {
		this.username = owner;
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
		return "0wn3r";
		
	}

	private void thenIPayCitaAdiestramiento() {
		driver.findElement(By.linkText("ADIESTRADORES")).click();
		assertEquals("1200",
				driver.findElement(By.xpath("//table[@id='adiestradoresTable']/tbody/tr/td[6]")).getText());
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.name("lastName")).click();
		driver.findElement(By.name("lastName")).clear();
		driver.findElement(By.name("lastName")).sendKeys("Franklin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("1200", driver.findElement(By.xpath("//tr[5]/td")).getText());
		driver.findElement(By.xpath("//a[contains(text(),'Citas Adiestramiento')]")).click();
		driver.findElement(By.xpath("//table[@id='citasAdiestramientoOwnersIdTable']/tbody/tr/td[8]")).click();
		assertEquals("false", driver
				.findElement(By.xpath("//table[@id='citasAdiestramientoOwnersIdTable']/tbody/tr/td[8]")).getText());
		driver.findElement(By.linkText("Pagar")).click();
		driver.findElement(By.xpath("//table[@id='citasAdiestramientoOwnersIdTable']/tbody/tr/td[8]")).click();
		assertEquals("true", driver
				.findElement(By.xpath("//table[@id='citasAdiestramientoOwnersIdTable']/tbody/tr/td[8]")).getText());
		driver.findElement(By.linkText("FIND OWNERS")).click();
		driver.findElement(By.name("lastName")).click();
		driver.findElement(By.name("lastName")).clear();
		driver.findElement(By.name("lastName")).sendKeys("Franklin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("1150", driver.findElement(By.xpath("//tr[5]/td")).getText());
		driver.findElement(By.linkText("ADIESTRADORES")).click();
		assertEquals("1250",
				driver.findElement(By.xpath("//table[@id='adiestradoresTable']/tbody/tr/td[6]")).getText());

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