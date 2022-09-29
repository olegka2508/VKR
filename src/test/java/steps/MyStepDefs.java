package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStepDefs {
    protected static WebDriver webDriver;

    @Given("Open the chrome and login page")
    public void openTheChromeAndLoginPage() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get("http://localhost:8087/login");
    }
    @When("User enter valid login and valid password")
    public void userEnterValidLoginAndValidPassword() throws InterruptedException {
        String password = "1";
        String username = "admin";
        loginBy(username, password);
    }

    private void loginBy(String username, String password) throws InterruptedException {
        String usernameField = "p:nth-child(2) > input";
        String passwordField = "p:nth-child(3) > input";
        String submitBtn = "p:nth-child(4) > input";
        webDriver.findElement(By.cssSelector(usernameField)).sendKeys(username);
        webDriver.findElement(By.cssSelector(passwordField)).sendKeys(password);
        webDriver.findElement(By.cssSelector(submitBtn)).submit();
        Thread.sleep(1000);
    }

    @Then("User should be able to login successfully")
    public void userShouldBeAbleToLoginSuccessfully() {
        String title = webDriver.getTitle();
        assertEquals("Dishes", title);
        webDriver.close();
        webDriver.quit();
    }


    @Given("Open the chrome and registration page")
    public void openTheChromeAndRegistrationPage() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get("http://localhost:8087/user/registration");}

    @When("User enter new login and password")
    public void userEnterNewLoginAndPassword() throws InterruptedException {
        String firstNameField = "#firstName";
        String lastNameField = "#lastName";
        String usernameField = "#username";
        String emailField = "#email";
        String passwordField = "#password";
        String confirmPasswordField = "#matchingPassword";
        String submitBtn = "button";
        webDriver.findElement(By.cssSelector(firstNameField)).sendKeys("ol");
        webDriver.findElement(By.cssSelector(lastNameField)).sendKeys("ni");
        username = String.valueOf(System.currentTimeMillis());
        webDriver.findElement(By.cssSelector(usernameField)).sendKeys(username);
        webDriver.findElement(By.cssSelector(emailField)).sendKeys("1@1");
        webDriver.findElement(By.cssSelector(passwordField)).sendKeys("1");
        webDriver.findElement(By.cssSelector(confirmPasswordField)).sendKeys("1");
        webDriver.findElement(By.cssSelector(submitBtn)).submit();
        Thread.sleep(1000);
        String title = webDriver.getTitle();
        assertEquals("Login - Dishes", title);
    }

    private String username;

    @Then("User should be able to registered successfully")
    public void userShouldBeAbleToRegisteredSuccessfully() throws InterruptedException {

        loginBy(username, "1");

        String title = webDriver.getTitle();
        assertEquals("Dishes", title);
        webDriver.close();
        webDriver.quit();
    }


    @Given("User should be able to login admin successfully")
    public void shouldBeAbleToLoginSuccessfully() {
        String title = webDriver.getTitle();
        assertEquals("Dishes", title);
    }

    @Given("User click to the button \"Add dish\"")
    public void clickToTheButtonAddDish() throws InterruptedException {
        String addDishBtn = "div:nth-child(2) > div > a.btn.btn-success";
        webDriver.findElement(By.cssSelector(addDishBtn)).click();
        Thread.sleep(3000);
        String title = webDriver.getTitle();
        assertEquals("Add - Dish", title);
    }
    @When("User filled dish attributes by valid values")
    public void filledDishAttributesByValidValues() throws InterruptedException {
        String idField = "#dishes0\\.id";
        String nameField = "#dishes0\\.name";
        String recipeField = "#dishes0\\.recipe";

        webDriver.findElement(By.cssSelector(idField)).sendKeys("1");
        webDriver.findElement(By.cssSelector(nameField)).sendKeys("chicken");
        webDriver.findElement(By.cssSelector(recipeField)).sendKeys("10 minutes fire");
    }
    @Then("User click to the button \"Save\"")
    public void clickToTheButtonSave() throws InterruptedException {
        String saveBtn = "#submitButton";
        webDriver.findElement(By.cssSelector(saveBtn)).submit();
        Thread.sleep(1000);
    }

    @Then("User has been redirected to findAll page")
    public void hasBeenRedirectedToFindAllPage() throws InterruptedException {
        String title = webDriver.getTitle();
        assertEquals("Dishes", title);
    }
    @Then("A new dish is displayed")
    public void newDishIsDisplayed() {
        String idTextField = "div:nth-child(1) > table > tbody > tr > td:nth-child(1)";
        String text = webDriver.findElement(By.cssSelector(idTextField)).getText();
        assertEquals("1", text);
        webDriver.close();
        webDriver.quit();
    }
}
