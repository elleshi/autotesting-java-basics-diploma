import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class RegistrationAndAuthorisationTests {
    private static Random random = new Random();
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    @After
    public void tearDown() throws IOException {
//        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(sourceFile, new File("C:\\Users\\Laptop101\\Desktop\\tmp\\screenshot.png"));
        driver.quit();

    }

    @Test
    public void registrationPage_FillingRegistrationForm_SuccessfulRegistration() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/register/");
        By nameFieldLocator = By.id("reg_username");
        By emailFieldLocator = By.id("reg_email");
        By passwordFieldLocator = By.id("reg_password");
        By registrationButtonLocator = By.className("woocommerce-form-register__submit");
        By successfulRegistrationLocator = By.className("content-page");


        String name = randomString();
        String endEmail = "@test.com";
        String email = randomString() + endEmail;
        String password = "12345fg";

        String expectedResult = "Регистрация завершена";

        // act
        driver.findElement(nameFieldLocator).sendKeys(name);
        driver.findElement(emailFieldLocator).sendKeys(email);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(registrationButtonLocator).click();
        String actualResult = driver.findElement(successfulRegistrationLocator).getText();

        // assert
        Assert.assertEquals("Пользователь не зарегистрировался", expectedResult, actualResult);
    }

    @Test
    public void registrationPage_FillingRegistrationFormWithExistingData_UnsuccessfulRegistration() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/register/");
        By nameFieldLocator = By.id("reg_username");
        By emailFieldLocator = By.id("reg_email");
        By passwordFieldLocator = By.id("reg_password");
        By registrationButtonLocator = By.className("woocommerce-form-register__submit");
        By errorMessageLocator = By.cssSelector("li strong");


        String name = "test";
        String email = "test@test.com";
        String password = "12345fg";

        String expectedResult = "Error:";

        // act
        driver.findElement(nameFieldLocator).sendKeys(name);
        driver.findElement(emailFieldLocator).sendKeys(email);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(registrationButtonLocator).click();
        String actualResult = driver.findElement(errorMessageLocator).getText();

        // assert
        Assert.assertEquals("Не появилось сообщение об ошибке при регистрации", expectedResult, actualResult);
    }
    @Test
    public void authorisationPage_FillingAuthorisationForm_SuccessfulSignIn() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/my-account/");
        By loginLocator = By.id("username");
        By passwordLocator = By.id("password");
        By signInButtonLocator = By.className("woocommerce-form-login__submit");
        By greetingLocator = By.xpath("//*[contains(text(),'Привет')]");


        String login = "Testtesttest";
        String password = "12345";

        String expectedResult = "Привет Testtesttest (Выйти)";

        // act
        driver.findElement(loginLocator).sendKeys(login);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(signInButtonLocator).click();
        String actualResult = driver.findElement(greetingLocator).getText();

        // assert
        Assert.assertEquals("Нет захода в ЛК", expectedResult, actualResult);
    }
    @Test
    public void authorisationPage_FillingAuthorisationFormWithWrongPass_UnsuccessfulSignIn() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/my-account/");
        By loginLocator = By.id("username");
        By passwordLocator = By.id("password");
        By signInButtonLocator = By.className("woocommerce-form-login__submit");
        By errorMessageLocator = By.xpath("//*[contains(text(),'Веденный')]");


        String login = "Testtesttest";
        String password = "qwerty";

        String expectedResult = "Веденный пароль для пользователя Testtesttest неверный. Забыли пароль?";

        // act
        driver.findElement(loginLocator).sendKeys(login);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(signInButtonLocator).click();
        String actualResult = driver.findElement(errorMessageLocator).getText();

        // assert
        Assert.assertEquals("Нет сообщения об ошибке", expectedResult, actualResult);
    }





    private static String randomString() {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";

        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase;

        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }
}
