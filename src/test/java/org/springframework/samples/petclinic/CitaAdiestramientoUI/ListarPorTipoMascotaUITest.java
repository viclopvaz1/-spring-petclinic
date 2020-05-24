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
public class ListarPorTipoMascotaUITest {

	@LocalServerPort
	private int port;

	private String username;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
//				String pathToGeckoDriver = "C:\\Users\\pepe1\\Documents\\geckodriver-v0.26.0-win64";
//				System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void listarTodasLasCitas() throws Exception {
		as("adiestrador1").whenIamLoggedIntheSystem().thenISeeAllCitasAdiestramiento();
	}

	@Test
	public void listarUnaCitaAdiestramiento() throws Exception {
		as("adiestrador1").whenIamLoggedIntheSystem().thenISeeOneCitasAdiestramiento();
	}

	@Test
	public void listarCitasAdiestramientoConUnTipoPetInvalidoOSinRegistros() throws Exception {
		as("adiestrador1").whenIamLoggedIntheSystem().thenIDontSeeAnyCitasAdiestramiento();
	}

	private ListarPorTipoMascotaUITest as(final String adiestrador) {
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

	private void thenISeeAllCitasAdiestramiento() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.id("pet.type.name")).click();
		driver.findElement(By.id("pet.type.name")).clear();
		driver.findElement(By.id("pet.type.name")).sendKeys("");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	private void thenISeeOneCitasAdiestramiento() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.id("pet.type.name")).click();
		driver.findElement(By.id("pet.type.name")).clear();
		driver.findElement(By.id("pet.type.name")).sendKeys("hamster");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("Basil", driver.findElement(By.xpath("//table[@id='citasAdiestramientoTable']/tbody/tr/td")).getText());


	}

	private void thenIDontSeeAnyCitasAdiestramiento() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.id("pet.type.name")).click();
		driver.findElement(By.id("pet.type.name")).clear();
		driver.findElement(By.id("pet.type.name")).sendKeys("lizard");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("has not been found", driver.findElement(By.id("citaAdiestramiento.errors")).getText());

	}

	private CharSequence passwordOf(String username) {
		return "adiestrador";
	}

	private ListarPorTipoMascotaUITest whenIamLoggedIntheSystem() {
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