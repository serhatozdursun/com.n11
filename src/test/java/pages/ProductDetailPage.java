package pages;

import base.TestBase;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import util.Configuration;
import util.ReadTxtFile;

import java.util.concurrent.TimeUnit;

import static base.PageBase.WAIT_TIME;

public class ProductDetailPage extends TestBase {
    RemoteWebDriver driver;

    public ProductDetailPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIME), this);
    }

    @FindBy(css = ".proName")
    private WebElement productName;

    @FindBy(css = ".price-cover ins")
    private WebElement newPrice;

    @FindBy(id = "instantPay")
    private WebElement instantPay;

    @FindBy(css = ".button-group .addBasketUnify")
    private WebElement addBasketUnify;

    @FindBy(css = ".myBasket")
    private WebElement myBasket;

    public ProductDetailPage assertProductNameAndPrice() throws Throwable {
        ReadTxtFile readTxtFile = new ReadTxtFile();
        Configuration configuration = new Configuration();
        String selectedProduct = readTxtFile.getTxtLine(configuration.getProductInfoFile(), 1);
        String selectedProductPrice = readTxtFile.getTxtLine(configuration.getProductInfoFile(), 2);
        Assert.assertEquals(selectedProduct, productName.getText());
        Assert.assertEquals(selectedProductPrice, newPrice.getText());
        return this;
    }

    public MyCartPage clickInstantPay() {
        clickElementWithJS(driver,instantPay);
        return new MyCartPage(driver);
    }

    public ProductDetailPage clickAddBasketUnify() {
        addBasketUnify.click();
        return this;
    }

    public MyCartPage clickMyBasket() {
        myBasket.click();
        return new MyCartPage(driver);
    }

    public MyCartPage addToProductBasketAndGoToBasket(){
        try {
            PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MICROSECONDS);
            return clickInstantPay();
        }catch (NoSuchElementException e){
            return clickAddBasketUnify()
                    .clickMyBasket();
        }
    }
}
