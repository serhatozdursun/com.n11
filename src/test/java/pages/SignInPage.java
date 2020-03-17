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

public class SignInPage  extends TestBase {
    RemoteWebDriver driver;

    public SignInPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIME), this);
    }

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    public SignInPage assertSignInPageIsLoad() {
        Assert.assertTrue(driver.getCurrentUrl().contains("n11.com/giris-yap"));
        assertePostaInputIsDisplayed();
        return this;
    }

    public SignInPage assertePostaInputIsDisplayed() {
        Assert.assertTrue(email.isDisplayed());
        return this;
    }

    public SignInPage typeEmail() throws IOException {
        ReadTestData readTestData = new ReadTestData();
        email.sendKeys(readTestData.getEmail());
        return this;
    }

    public SignInPage typePassword() throws IOException {
        ReadTestData readTestData = new ReadTestData();
        password.sendKeys(readTestData.getPassword());
        return this;
    }

    public HomePage clickSignIn(){
        waitForElementToBeClickable(driver,loginButton);
        clickElementWithJS(driver,loginButton);
        return new HomePage(driver);
    }
}
