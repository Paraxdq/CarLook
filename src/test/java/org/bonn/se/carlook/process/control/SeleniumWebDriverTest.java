package org.bonn.se.carlook.process.control;

import org.bonn.se.carlook.services.util.Globals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumWebDriverTest {
    public static WebDriver driver = null;

    @BeforeAll
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", Globals.CHROMEDRIVER_LOCATION);
        driver = new ChromeDriver();
    }

    @Test
    public void TestRegistrieren() {
        if(driver == null)
            return;

        driver.navigate().to("http://localhost:8080/#!login");
        driver.manage().window().maximize();

        String randomEMail = TestHelper.generateRandomEmail(8, "@seleniumtest.de");

        driver.findElement(By.id("tfRegisterForeName")).sendKeys("Fritz");
        driver.findElement(By.id("tfRegisterSurName")).sendKeys("Mueller");
        driver.findElement(By.id("tfRegisterEMail")).sendKeys(randomEMail);
        driver.findElement(By.id("tfRegisterPassword")).sendKeys("123");
        driver.findElement(By.id("tfRegisterPasswordConfirm")).sendKeys("123");
        driver.findElement(By.id("btnRegister")).click();
    }

    @Test
    public void TestLogin() {
        if(driver == null)
            return;

        driver.navigate().to("http://localhost:8080/#!login");
        driver.manage().window().maximize();

        driver.findElement(By.id("tfLoginEMail")).sendKeys("123@test.de");
        driver.findElement(By.id("tfLoginPassword")).sendKeys("123");
        driver.findElement(By.id("btnLogin")).click();
    }
}
