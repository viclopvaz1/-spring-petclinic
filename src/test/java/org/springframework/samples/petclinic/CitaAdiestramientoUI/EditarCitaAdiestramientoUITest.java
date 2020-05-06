package org.springframework.samples.petclinic.CitaAdiestramientoUI;

import static org.junit.Assert.*;

import org.junit.jupiter.api.extension.ExtendWith;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditarCitaAdiestramientoUITest {
	@LocalServerPort
	private int port;

	private String username;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
//		String pathToGeckoDriver = "C:\\Users\\pepe1\\Documents\\geckodriver-v0.26.0-win64";
//		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void editarCitaOperacionConErrores() throws Exception {

		as("adiestrador2").whenIamLoggedIntheSystem().thenIPressBasilEditButton()
		.andThenIEditCitaAdiestramientoWithErrors();
	}

	@Test
	public void editarCitaOperacionSinErrores() throws Exception {

		as("adiestrador2").whenIamLoggedIntheSystem().thenIPressBasilEditButton().andThenIEditCitaAdiestramiento();
	}

	private EditarCitaAdiestramientoUITest as(final String adiestrador) {
		this.username = adiestrador;
		this.driver.get("http://localhost:" + this.port);
		driver.findElement(By.linkText("LOGIN")).click();

		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("adiestrador1");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("adiestrador");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		return this;
	}

	private EditarCitaAdiestramientoUITest thenIPressBasilEditButton() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
	    driver.findElement(By.linkText("CITAS ADIESTRAMIENTO")).click();
	    driver.findElement(By.linkText("Basil")).click();
	    driver.findElement(By.linkText("Edit")).click();
	  

		return this;
	}


	private void andThenIEditCitaAdiestramiento() {
	
		   driver.findElement(By.id("hora")).click();
		    driver.findElement(By.id("hora")).clear();
		    driver.findElement(By.id("hora")).sendKeys("18:00");
		    driver.findElement(By.id("duracion")).click();
		    driver.findElement(By.id("duracion")).clear();
		    driver.findElement(By.id("duracion")).sendKeys("90");
		    driver.findElement(By.id("precio")).click();
		    driver.findElement(By.id("precio")).clear();
		    driver.findElement(By.id("precio")).sendKeys("180.0");
		    new Select(driver.findElement(By.id("tipoAdiestramiento"))).selectByVisibleText("Adiestramiento en obediencia basica");
		    driver.findElement(By.xpath("//option[@value='Adiestramiento en obediencia basica']")).click();
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    driver.findElement(By.xpath("//body/div")).click();
		    assertEquals("Cita Adiestramiento Information", driver.findElement(By.xpath("//h2")).getText());
		  
		    assertEquals("90", driver.findElement(By.xpath("//tr[4]/td")).getText());
		
		    assertEquals("Adiestramiento en obediencia basica", driver.findElement(By.xpath("//tr[7]/td")).getText());
		
		
	}

	private void andThenIEditCitaAdiestramientoWithErrors() {
		driver.findElement(By.id("duracion")).click();
		driver.findElement(By.id("duracion")).clear();
		driver.findElement(By.id("duracion")).sendKeys("");
		driver.findElement(By.id("hora")).click();
		driver.findElement(By.id("hora")).clear();
		driver.findElement(By.id("hora")).sendKeys("");
		driver.findElement(By.id("fechaInicio")).click();
		driver.findElement(By.id("fechaInicio")).clear();
		driver.findElement(By.id("fechaInicio")).sendKeys("");
		driver.findElement(By.id("precio")).click();
		driver.findElement(By.id("precio")).clear();
		driver.findElement(By.id("precio")).sendKeys("");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div/div")).click();
		assertEquals("no puede ser null", driver
				.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div/div/span[2]")).getText());
		driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[2]/div")).click();
		assertEquals("no puede ser null", driver
				.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[2]/div/span[2]")).getText());
		driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[3]/div")).click();
		assertEquals("no puede ser null", driver
				.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[3]/div/span[2]")).getText());
		driver.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[4]/div")).click();
		assertEquals("no puede ser null", driver
				.findElement(By.xpath("//form[@id='add-citaAdiestramiento-form']/div/div[4]/div/span[2]")).getText());
	}

	
	
	 
	  private CharSequence passwordOf(String username) {
			return "adiestrador";
		}
	
	

		private EditarCitaAdiestramientoUITest whenIamLoggedIntheSystem() {
			
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
