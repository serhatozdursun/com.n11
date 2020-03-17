package pages;

import base.TestBase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import util.Configuration;
import util.ReadTxtFile;

import java.util.List;
import java.util.NoSuchElementException;

import static base.PageBase.WAIT_TIME;

public class MyCartPage extends TestBase {
    RemoteWebDriver driver;
    private String productsCss=".productGroup .prod";

    public MyCartPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIME), this);
    }

    @FindBy(css = ".basket")
    public WebElement basket;

    @FindBy(css = ".productGroup .prod")
    public List<WebElement> products;

    @FindBy(css = ".spinnerUp")
    public List<WebElement> spinnerUp;

    @FindBy(css = ".quantity")
    public List<WebElement> quantityElm;

    @FindBy(css = ".removeProd")
    public List<WebElement> removeProd;

    @FindBy(css = ".prodDescription")
    public List<WebElement> prodDescription;

    @FindBy(css = ".title")
    private WebElement title;

    public MyCartPage assertMyCartPageIsLoad() {
        assertBasketIsDisplayed();
        Assert.assertTrue(driver.getCurrentUrl().contains("n11.com/sepetim"));
        return this;
    }

    public MyCartPage assertBasketIsDisplayed() {
        Assert.assertTrue(basket.isDisplayed());
        return this;
    }

    public MyCartPage assertProductPrice() throws Throwable {
        ReadTxtFile readTxtFile = new ReadTxtFile();
        Configuration configuration = new Configuration();
        By by = By.cssSelector(".productGroup .prod");
        waitForElement(driver, by);
        List<WebElement> basketProductsList = driver.findElements(by);
        for (int i = 0; i < basketProductsList.size(); i++) {
            String selectedProductName = readTxtFile.getTxtLine(configuration.getProductInfoFile(), 1);
            String basketProductName = prodDescription.get(i).getText();
            if (!basketProductName.equals(selectedProductName)) {
                removeProd.get(i).click();
                basketProductsList = driver.findElements(By.cssSelector(".productGroup .prod"));
                i = 0;
            }
        }
        String productPrice = products.get(0).findElement(By.cssSelector(".priceArea")).getText();
        String selectedProductPrice = readTxtFile.getTxtLine(configuration.getProductInfoFile(), 2);
        Assert.assertEquals(selectedProductPrice, productPrice);

        return this;
    }

    public MyCartPage clickSpinnerUp() {
        clickElementWithJS(driver, spinnerUp.get(0));
        return this;
    }

    public MyCartPage checkQuantity(int quantity) {
        Assert.assertEquals(quantityElm.get(0).getAttribute("value"), String.valueOf(quantity));
        return this;
    }

    public MyCartPage clicRemoveProd() {
        removeProd.get(0).click();
        return this;
    }

    public MyCartPage removeAllElement() {
        int size = removeProd.size();
        for (int i = 0; i < size; i++) {
            try {
                clicRemoveProd();
                i = 0;
                Thread.sleep(500);
            } catch (NoSuchElementException | InterruptedException e) {
                break;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return this;
    }

    public MyCartPage checkBasketIsEmpty() {
        Assert.assertEquals("Sepetiniz Boş", title.getText());
        return this;
    }


}
