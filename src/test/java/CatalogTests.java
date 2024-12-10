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

public class CatalogTests {
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
    public void catalog_GoingToRandomProduct_ProductIsOpen() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By productLocator = By.cssSelector(".product:nth-child(7) .attachment-shop_catalog");
        By productTitleLocator = By.className("product_title");
        String expectedResult = driver.findElement(By.cssSelector(".product:nth-child(7) h3")).getText();
        //act
        driver.findElement(productLocator).click();
        String actualResult = driver.findElement(productTitleLocator).getText();

        // assert
        Assert.assertEquals("Не произошел переход в нужную карточку товара", expectedResult,actualResult);
    }

    @Test
    public void catalog_GoingToCategory_TitleIsAMatch() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By catalogOnUpperMenuLocator = By.xpath("//a[text()='Каталог']");
        By categoryLocator = By.id("menu-item-180");
        By categoryTitleLocator = By.className("entry-title");
        String expectedResult = "КНИГИ";
        //act
        Actions action = new Actions(driver);
        WebElement catalogEl = driver.findElement(catalogOnUpperMenuLocator);
        WebElement categoryEl = driver.findElement(categoryLocator);
        action.moveToElement(catalogEl).build().perform();
        action.moveToElement(categoryEl).click().build().perform();
        String actualResult = driver.findElement(categoryTitleLocator).getText();

        // assert
        Assert.assertEquals("Не произошел переход на страницу категории", expectedResult,actualResult);
    }
    @Test
    public void catalog_CheckPagination_TitleIsAMatch() {
        //arrange
        driver.navigate().to("https://intershop5.skillbox.ru/product-category/catalog/");
        By paginatorLocator = By.linkText("4");
        By pageLocator = By.cssSelector(".woocommerce-breadcrumb > span");
        String expectedResult = "Page 4";
        //act
        driver.findElement(paginatorLocator).click();
        String actualResult = driver.findElement(pageLocator).getText();

        // assert
        Assert.assertEquals("Не произошел переход на ругую страницу каталога", expectedResult,actualResult);
    }


}
