package ua.tqs.projects.individual.web;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class InterfaceTests
{	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
	
	@Before
	public void setUp() throws Exception
	{
		driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testInterface() throws Exception
	{
		driver.get(String.format("http://localhost:%d/", port));
		driver.findElement(By.id("day")).click();
		new Select(driver.findElement(By.id("day"))).selectByVisibleText("Tomorrow");
		driver.findElement(By.id("day")).click();
		driver.findElement(By.id("method")).click();
		new Select(driver.findElement(By.id("method"))).selectByVisibleText("By City");
		driver.findElement(By.id("method")).click();
		driver.findElement(By.id("days")).click();
		new Select(driver.findElement(By.id("days"))).selectByVisibleText("3");
		driver.findElement(By.id("days")).click();
		driver.findElement(By.id("city")).click();
		new Select(driver.findElement(By.id("city"))).selectByVisibleText("Braga");
		driver.findElement(By.id("city")).click();
	}

	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString))
			Assert.fail(verificationErrorString);
	}
}
