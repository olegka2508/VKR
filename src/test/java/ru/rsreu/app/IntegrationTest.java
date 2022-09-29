package ru.rsreu.app;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    protected static WebDriver webDriver;

    @Before
    public void start() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void end() {
        if (webDriver != null)
        webDriver.quit();
    }

    @Test
    public void smokeTest() {
        webDriver.get("http://localhost:8081/login");
    }

    @Test
    public void successAuthAdminTest() throws InterruptedException {
        webDriver.get("http://localhost:8080/login");
        String usernameField = "p:nth-child(2) > input";
        String passwordField = "p:nth-child(3) > input";
        String submitBtn = "p:nth-child(4) > input";
        webDriver.findElement(By.cssSelector(usernameField)).sendKeys("admin");
        webDriver.findElement(By.cssSelector(passwordField)).sendKeys("1");
        webDriver.findElement(By.cssSelector(submitBtn)).submit();
        Thread.sleep(1000);
        String title = webDriver.getTitle();
        assertEquals("Dishes", title);
    }
//
//    @Test
//    public void smokeTest() {
//        webDriver.get("http://localhost:8081/login");
//    }


}
