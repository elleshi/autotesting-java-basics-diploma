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

public class CartTests {
    private WebDriver driver;
    private WebDriverWait wait;

    By productAddToCartButtonLocator = By.cssSelector(".product:nth-child(10) .button");
    By productNameInCartLocator = By.cssSelector(".woocommerce-cart-form__cart-item > .product-name");
    By cartLinkLocator = By.cssSelector("#menu-item-29 > a");
    By productNameLocator = By.cssSelector(".product:nth-child(10) h3");
    By cartUpdatedMessageLocator = By.className("woocommerce-message");

    By totalProductPriceLocator = By.cssSelector(".product-subtotal bdi");

    By readMoreLocator = By.className("added_to_cart");

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
    public void cart_AddToCartFromCatalog_ProductIsPresent() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");

        String expectedResult = driver.findElement(productNameLocator).getText();

        // act
        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();
        String actualResult = driver.findElement(productNameInCartLocator).getText();

        // assert
        Assert.assertEquals("Отсутвует добавленный товар в корзине", expectedResult,actualResult);

    }
    @Test
    public void cart_AddToCartFromProductPage_ProductIsPresent() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By productLocator = By.cssSelector(".product:nth-child(10) .attachment-shop_catalog");
        By addToCartLocator = By.name("add-to-cart");

        String expectedResult = driver.findElement(productNameLocator).getText();

        // act
        driver.findElement(productLocator).click();
        driver.findElement(addToCartLocator).click();
        driver.findElement(cartLinkLocator).click();
        String actualResult = driver.findElement(productNameInCartLocator).getText();

        // assert
        Assert.assertEquals("Отсутвует добавленный товар в корзине", expectedResult,actualResult);

    }
    @Test
    public void cart_IncreasingAmountOfProduct_TotalSumHasIncreasedAndCartUpdatedMessageIsDisplayed(){
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By quantityInputLocator = By.xpath("//input[contains(@id,'quantity')]");

        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();

        String startPrice = (driver.findElement(totalProductPriceLocator).getText());
        int expectedResult = Integer.parseInt(startPrice.substring(0,5))*2;


        //act
        driver.findElement(quantityInputLocator).click();
        driver.findElement(quantityInputLocator).sendKeys(Keys.BACK_SPACE);
        driver.findElement(quantityInputLocator).sendKeys("2");
        driver.findElement(quantityInputLocator).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartUpdatedMessageLocator));
        String endPrice = driver.findElement(totalProductPriceLocator).getText();
        int actualResult = Integer.parseInt(endPrice.substring(0,5));

        // assert
        Assert.assertTrue("Не появилось сообщение об обновлении корзины", driver.findElement(cartUpdatedMessageLocator).isDisplayed());
        Assert.assertEquals("Общая стоимость товара не увеличилась", expectedResult,actualResult);


    }
    @Test
    public void cart_UsingCertificate_TotalSumHasChanged(){
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By totalPriceLocator = By.cssSelector(".order-total bdi");
        By loaderLocator = By.className("woocommerce-cart-form-processing");


        By certificateInputLocator = By.id("coupon_code");
        By certificateButtonLocator = By.name("apply_coupon");

        String certificate = "sert500";

        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();

        String startPrice = (driver.findElement(totalProductPriceLocator).getText());
        int expectedResult = Integer.parseInt(startPrice.substring(0,5))-500;



        //act
        driver.findElement(certificateInputLocator).sendKeys(certificate);
        driver.findElement(certificateButtonLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        String endPrice = driver.findElement(totalPriceLocator).getText();
        int actualResult = Integer.parseInt(endPrice.substring(0,5));

        // assert
        Assert.assertEquals("Сертификат не применился", expectedResult,actualResult);


    }
    @Test
    public void cart_DeletingProduct_CartUpdatedMessageHasAppeared(){
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By deleteButtonLocator = By.className("remove");

        String expectedResult = "“Apple Watch 6” удален. Вернуть?";

        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();


        //act
        driver.findElement(deleteButtonLocator).click();
        String actualResult = driver.findElement(cartUpdatedMessageLocator).getText();

        // assert
        Assert.assertTrue("Не появилось сообщение об обновлении корзины", driver.findElement(cartUpdatedMessageLocator).isDisplayed());
        Assert.assertEquals("Не удалился товар из корзины", expectedResult,actualResult);


    }
    @Test
    public void cart_restoringProduct_ProductReappearedInCart(){
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By deleteButtonLocator = By.className("remove");
        By restoreLinkLocator = By.className("restore-item");

        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();
        driver.findElement(deleteButtonLocator).click();


        //act
        driver.findElement(restoreLinkLocator).click();

        // assert
        Assert.assertTrue("Не появился товар в корзине", driver.findElement(productNameInCartLocator).isDisplayed());


    }
    @Test
    public void cart_OrderRegistration_CheckoutPageIsOpened(){
        // arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By checkoutButtonLocator = By.className("checkout-button");
        By checkoutPageTitleLocator = By.className("post-title");

        String expectedResult = "Оформление заказа";

        driver.findElement(productAddToCartButtonLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(readMoreLocator));
        driver.findElement(cartLinkLocator).click();


        //act
        driver.findElement(checkoutButtonLocator).click();
        String actualResult = driver.findElement(checkoutPageTitleLocator).getText();

        // assert
        Assert.assertEquals("Не роизошел переход на страницу оформления заказа", expectedResult, actualResult);


    }
}
