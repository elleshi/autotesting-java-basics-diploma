import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class MainPageTests {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
    @AfterClass
    public static void tearDown() throws IOException {
//        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(sourceFile, new File("C:\\Users\\Laptop101\\Desktop\\tmp\\screenshot.png"));
        driver.quit();

    }
    @Test
    public void mainPage_CheckAsideCount_EightAsideBlocksOnPage() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/");
        By asideBlockLocator = By.cssSelector("aside");
        int asideCount = 8;
        //act
        List<WebElement> asideBlocksLocator = driver.findElements(asideBlockLocator);
        // assert
        Assert.assertEquals("Неверное количество блоков на странице", asideCount, asideBlocksLocator.size());
    }
    @Test
    public void mainPage_GoingToBookCatalogOnFirstDivision_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By bookDivLocator = By.xpath("//h4[text()='Книги']");
        By bookCatalogTitleLocator = By.xpath("//h1[text()='Книги']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(bookDivLocator));
        String expected = driver.findElement(bookDivLocator).getText();

        //act
        driver.findElement(bookDivLocator).click();
        String actual = driver.findElement(bookCatalogTitleLocator).getText();
        // assert
        Assert.assertEquals("Не совпадают заголовки разделов на главной странице и странице перехода", expected, actual);
    }
    @Test
    public void mainPage_GoingToTabletCatalogOnFirstDivision_TitleIsAMatch() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/");
        By tabletDivLocator = By.xpath("//h4[text()='Планшеты']");
        By tabletCatalogTitleLocator = By.xpath("//h1[text()='Планшеты']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(tabletDivLocator));
        String expected = driver.findElement(tabletDivLocator).getText();

        //act
        driver.findElement(tabletDivLocator).click();
        String actual = driver.findElement(tabletCatalogTitleLocator).getText();
        // assert
        Assert.assertEquals("Не совпадают заголовки разделов на главной странице и странице перехода", expected, actual);
    }
    @Test
    public void mainPage_GoingToCameraCatalogOnFirstDivision_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By cameraDivLocator = By.xpath("//h4[text()='Фотоаппараты']");
        By cameraCatalogTitleLocator = By.xpath("//h1[text()='Фото/видео']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(cameraDivLocator));
        String expected = driver.findElement(cameraDivLocator).getText();
        //act
        driver.findElement(cameraDivLocator).click();
        String actual = driver.findElement(cameraCatalogTitleLocator).getText();
        // assert
        Assert.assertEquals("Не совпадают заголовки разделов на главной странице и странице перехода", expected, actual);
    }

    @Test
    public void mainPage_GoingToCatalogOnUpperMenu_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By catalogLocator = By.xpath("//a[text()='Каталог']");

        //act
        driver.findElement(catalogLocator).click();
        String expectedTitle = "Каталог — Skillbox";
        String actualTitle = driver.getTitle();
        // assert
        Assert.assertEquals("Нет перехода на страницу каталога", expectedTitle, actualTitle);
    }
    @Test
    public void mainPage_GoingToMyAccountOnUpperMenu_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By myAccountLocator = By.xpath("//*[@id='menu-item-30']/a");

        //act
        driver.findElement(myAccountLocator).click();
        String expectedTitle = "Мой аккаунт — Skillbox" ;
        String actualTitle = driver.getTitle();
        // assert
        Assert.assertEquals("Нет перехода на страницу мой аккаунт", expectedTitle, actualTitle);
    }
    @Test
    public void mainPage_GoingToCartOnUpperMenu_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By cartLocator = By.xpath("//*[@id='menu-item-29']/a");

        //act
        driver.findElement(cartLocator).click();
        String expectedTitle = "Корзина — Skillbox";
        String actualTitle = driver.getTitle();
        // assert
        Assert.assertEquals("Нет перехода на страницу корзины", expectedTitle, actualTitle);
    }
    @Test
    public void mainPage_GoingToOrderPageOnUpperMenu_TitleIsAMatch() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");
        By orderLocator = By.xpath("(//*[text()='Оформление заказа'])[1]");

        //act
        driver.findElement(orderLocator).click();
        String expectedTitle = "Оформление заказа  — Skillbox";
        String actualTitle = driver.getTitle();
        // assert
        Assert.assertEquals("Нет перехода на страницу оформления заказа", expectedTitle, actualTitle);
    }
    @Test
    public void mainPage_CheckSaleLabel_LabelIsDisplayed() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");

        By saleLabelLocator = By.xpath("(//*[@class='onsale'])[5]");
        String expectedText ="Скидка!";

        // act
        String actualText = driver.findElement(saleLabelLocator).getText();

        // assert
        Assert.assertTrue("Нет лейбла Скидка в блоке распродажи",driver.findElement(saleLabelLocator).isDisplayed());
        Assert.assertEquals("Неправильный лейбл в блоке распродажи", expectedText, actualText);
    }

    @Test
    public void mainPage_CheckNewLabel_LabelIsDisplayed() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");

        By newLabelLocator = By.xpath("(//*[@class='label-new'])[5]");
        String expectedText ="Новый!";

        //act
        String actualText = driver.findElement(newLabelLocator).getText();

        // assert
        Assert.assertTrue("Нет лейбла Скидка в блоке распродажи",driver.findElement(newLabelLocator).isDisplayed());
        Assert.assertEquals("Неправильный лейбл в блоке распродажи", expectedText, actualText);
    }
    @Test
    public void mainPage_CheckViewedProductsBlock_BlockIsAppeared() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");

        By productLocator = By.xpath("(//li[contains(@class,'span3 wow')])[5]");
        By mainPageLocator = By.xpath("//*[@id='menu-item-26']/a");
        By viewedProductsLocator = By.xpath("//*[text()='Просмотренные товары']");

        //act
        wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
        driver.findElement(productLocator).click();
        driver.findElement(mainPageLocator).click();

        // assert
        Assert.assertTrue("Не отобразился блок просмотренных товаров",driver.findElement(viewedProductsLocator).isDisplayed());
    }
    @Test
    public void mainPage_ClickCartButton_ButtonHasChangedToReadMore() {
        //arrange

        driver.navigate().to("https://intershop5.skillbox.ru/");

        By cartButtonLocator = By.xpath("(//a[contains(@class,'add_to_cart_button')])[5]");
        By readMoreButtonLocator = By.xpath("(//*[contains(@class,'added_to_cart')])");
        String expectedText = "ПОДРОБНЕЕ";
        Actions action = new Actions(driver);
        WebElement webElement = driver.findElement(cartButtonLocator);

        //act
        action.moveToElement(webElement);
        action.click().build().perform();
        String actualText = driver.findElement(readMoreButtonLocator).getText();

        // assert
        Assert.assertEquals("Не поменялась кнопка В корзину на Подробнее", expectedText, actualText);
    }
}
