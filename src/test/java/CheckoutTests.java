import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class CheckoutTests {
    private static Random random = new Random();
    private WebDriver driver;
    private WebDriverWait wait;

    By productAddToCartButtonLocator = By.cssSelector(".product:nth-child(10) .button");
    By cartLinkLocator = By.cssSelector("#menu-item-29 > a");
    By checkoutButtonLocator = By.className("checkout-button");

    By thankYouMessageLocator = By.className("woocommerce-thankyou-order-received");

    By placeOrderLocator = By.id("place_order");
    By readMoreLocator = By.className("added_to_cart");

    String login = "Testtesttest";
    String password = "12345";

    String expectedResult = "Спасибо! Ваш заказ был получен.";


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    @After
    public void tearDown() throws IOException {
//        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(sourceFile, new File("C:\\Users\\Laptop101\\Desktop\\tmp\\screenshot.png"));
        driver.quit();

    }
    @Test
    public void checkout_FillingAndConfirmingOrderNewUser_SuccessfulOrder() {
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By myAccountLocator = By.xpath("//*[@id='menu-item-30']/a");
        By registerOnMyAccountButtonLocator = By.className("custom-register-button");
        By nameFieldLocator = By.id("reg_username");
        By emailFieldLocator = By.id("reg_email");
        By passwordFieldLocator = By.id("reg_password");
        By registrationButtonLocator = By.className("woocommerce-form-register__submit");

        By nameLocator = By.id("billing_first_name");
        By lastNameLocator = By.id("billing_last_name");
        By addressLocator = By.id("billing_address_1");
        By cityLocator = By.id("billing_city");
        By stateLocator = By.id("billing_state");
        By postcodeLocator = By.id("billing_postcode");
        By phoneNumberLocator = By.id("billing_phone");

        String name = randomString();
        String endEmail = "@test.com";
        String email = randomString() + endEmail;
        String password = "12345fg";

        String inputs = "test";
        String phone = "89000000000";


        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(myAccountLocator).click();
        driver.findElement(registerOnMyAccountButtonLocator).click();
        driver.findElement(nameFieldLocator).sendKeys(name);
        driver.findElement(emailFieldLocator).sendKeys(email);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(registrationButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        // act

        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(nameLocator).sendKeys(inputs);
        driver.findElement(lastNameLocator).sendKeys(inputs);
        driver.findElement(addressLocator).sendKeys(inputs);
        driver.findElement(cityLocator).sendKeys(inputs);
        driver.findElement(stateLocator).sendKeys(inputs);
        driver.findElement(postcodeLocator).sendKeys(inputs);
        driver.findElement(phoneNumberLocator).sendKeys(phone);
        driver.findElement(placeOrderLocator).click();

        String actualResult = driver.findElement(thankYouMessageLocator).getText();

        // assert
        Assert.assertEquals("Не оформился заказ", expectedResult, actualResult);
    }

    @Test
    public void checkout_ConfirmingOrderWithDeliveryPaymentMethodOldUser_SuccessfulOrder() {
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");

        By authorisationButtonLocator = By.className("showlogin");
        By loginLocator = By.id("username");
        By passwordLocator = By.id("password");
        By signInButtonLocator = By.className("woocommerce-form-login__submit");
        By paymentMethodRadioButtonLocator = By.id("payment_method_cod");


        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();

        // act

        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(authorisationButtonLocator).click();
        driver.findElement(loginLocator).sendKeys(login);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(signInButtonLocator).click();

        WebElement paymentButtonEl = driver.findElement(paymentMethodRadioButtonLocator);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(paymentButtonEl)));
        driver.findElement(paymentMethodRadioButtonLocator).click();
        driver.findElement(placeOrderLocator).click();


        String actualResult = driver.findElement(thankYouMessageLocator).getText();

        // assert
        Assert.assertEquals("Не оформился заказ", expectedResult, actualResult);


    }

    @Test
    public void checkout_ConfirmingOrderSignedInUser_OrderNumberIsAMatch() {
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/");

        By signInLinkLocator = By.className("account");
        By loginLocator = By.id("username");
        By passwordLocator = By.id("password");
        By signInButtonLocator = By.className("woocommerce-form-login__submit");
        By catalogLocator = By.xpath("//a[text()='Каталог']");
        By orderLocator = By.xpath("(//*[text()='Оформление заказа'])[1]");
        By orderNumberLocator = By.cssSelector(".order strong");
        By myAccountLocator = By.xpath("//*[@id='menu-item-30']/a");
        By myOrdersLocator = By.cssSelector(".woocommerce-MyAccount-navigation-link--orders a");
        By orderNumberOnMyAccountLocator = By.className("woocommerce-orders-table__cell-order-number");

        driver.findElement(signInLinkLocator).click();
        driver.findElement(loginLocator).sendKeys(login);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(signInButtonLocator).click();
        driver.findElement(catalogLocator).click();
        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));

        // act

        driver.findElement(orderLocator).click();
        WebElement placeOrderEl = driver.findElement(placeOrderLocator);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(placeOrderEl)));
        driver.findElement(placeOrderLocator).click();

        String expectedOrderNumber = driver.findElement(orderNumberLocator).getText();

        driver.findElement(myAccountLocator).click();
        driver.findElement(myOrdersLocator).click();

        String OrderNumber = driver.findElement(orderNumberOnMyAccountLocator).getText();
        String actualOrderNumber = OrderNumber.substring(1);

        // assert
        Assert.assertEquals("Номер оформленного заказа и заказа на странице аккаунта не совпадают", expectedOrderNumber, actualOrderNumber);


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
