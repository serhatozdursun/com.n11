package pages;

import base.TestBase;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import util.ReadTestData;

import java.io.IOException;

import static base.PageBase.WAIT_TIME;

public class HomePage extends TestBase {
    RemoteWebDriver driver;

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIME), this);
    }

    @FindBy(css = ".topMainSlide")
    private WebElement homePageBanner;

    @FindBy(css = ".btnSignIn")
    private WebElement btnSignIn;

    @FindBy(css = ".user")
    private WebElement user;

    @FindBy(id = "searchData")
    private WebElement searchData;

    @FindBy(css = ".searchBtn")
    private WebElement searchBtn;

    public HomePage assertHomePageIsLoad() throws IOException {

        Assert.assertTrue("home page not loaded", driver.getCurrentUrl().contains("n11.com"));
        assertHomePageIsDisplayed();
        return this;
    }

    public HomePage assertHomePageIsDisplayed() {
        Assert.assertTrue(homePageBanner.isDisplayed());
        return this;
    }

    public SignInPage clickBtnSignIn() {
        btnSignIn.click();
        return new SignInPage(driver);
    }

    public HomePage assertIsSignIn() throws IOException {
        ReadTestData readTestData = new ReadTestData();
        Assert.assertEquals(readTestData.getUserName(), user.getText());
        return this;
    }

    public HomePage typeSearchKeyWord() throws IOException {
        ReadTestData readTestData = new ReadTestData();
        searchData.sendKeys(readTestData.getSearchKeyword());
        return this;
    }

    public SearchResultPage clickSearch() {
        waitForElementToBeClickable(driver, searchBtn);
        clickElementWithJS(driver, searchBtn);
        return new SearchResultPage(driver);
    }

}
