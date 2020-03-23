package pages;

import base.PageBase;
import base.TestBase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import util.Configuration;
import util.ReadTestData;
import util.WriteToTxt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import static base.PageBase.WAIT_TIME;

public class SearchResultPage extends TestBase {
    RemoteWebDriver driver;

    public SearchResultPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIME), this);
    }

    @FindBy(css = ".resultListGroup")
    public WebElement resultListGroup;

    @FindBy(css = ".pagination a")
    public List<WebElement> pagination;

    @FindBy(css = ".resultListGroup div[id].columnContent")
    public List<WebElement> productList;


    public SearchResultPage assertSearchPageIsLoad() throws IOException, InterruptedException {
        ReadTestData readTestData = new ReadTestData();
        if (PageBase.BROWSER.equalsIgnoreCase("firefox"))
            Thread.sleep(1500);
        Assert.assertTrue("link hatalı; " + driver.getCurrentUrl(), driver.getCurrentUrl().contains("arama?q=" + readTestData.getSearchKeyword()));
        assertSearchResultIsDisplayed();
        return this;
    }

    public SearchResultPage assertSearchResultIsDisplayed() {
        Assert.assertTrue(resultListGroup.isDisplayed());
        return this;
    }

    public SearchResultPage clickPagination(int pageNumber) throws InterruptedException {
        moveElement(driver, pagination.get(pageNumber - 1));
        pagination.get(pageNumber - 1).click();
        return this;
    }

    public SearchResultPage assertPageIndex(int pageNumber) throws InterruptedException {
        for (WebElement pageIndexElm : pagination) {
            if (pageIndexElm.getAttribute("class").contains("active")) {
                Assert.assertEquals(pageIndexElm.getText(), String.valueOf((pageNumber)));
                Assert.assertEquals(pageIndexElm.getAttribute("href"), driver.getCurrentUrl());
            }
        }
        return this;
    }

    public ProductDetailPage clickRandomProduct() throws InterruptedException, IOException {
        if (PageBase.BROWSER.equalsIgnoreCase("firefox"))
            Thread.sleep(1200);
        Random random = new Random();
        int elmindex = productList.size() - 1;
        WebElement randomElm = productList.get(random.nextInt(elmindex));
        String productNamme = randomElm.findElement(By.cssSelector(".productName")).getText();
        String productPrice = randomElm.findElement(By.cssSelector(".newPrice ins")).getText();

        Configuration configuration = new Configuration();
        WriteToTxt writeToTxt = new WriteToTxt();
        PrintWriter txtFile = writeToTxt.createWriter(configuration.getProductInfoFile());
        writeToTxt.writeToTxt(txtFile, productNamme);
        writeToTxt.writeToTxt(txtFile, productPrice);
        txtFile.close();
        WebElement randomProductName= randomElm.findElement(By.cssSelector(".productName"));
        moveElement(driver,randomProductName);
        randomProductName.click();
        return new ProductDetailPage(driver);
    }
}
