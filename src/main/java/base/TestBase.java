package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

    public JavascriptExecutor javascriptExecutor(RemoteWebDriver driver) {
        return driver;
    }

    public void clickElementWithJS(RemoteWebDriver driver, WebElement element) {

        javascriptExecutor(driver).executeScript("arguments[0].click();", element);
    }

    public WebDriverWait wait(RemoteWebDriver driver) {
        return new WebDriverWait(driver, 15);
    }

    public void waitForElementToBeClickable(RemoteWebDriver driver, WebElement webElement) {
        wait(driver).until(ExpectedConditions.elementToBeClickable(webElement));
    }
    public void waitForElement(RemoteWebDriver driver, By by) {
        wait(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void moveElement(RemoteWebDriver driver, WebElement webElement) throws InterruptedException {
        Actions actions = new Actions(driver);
        if (PageBase.BROWSER.equalsIgnoreCase("chrome"))
            actions.moveToElement(webElement).build().perform();
        else if (PageBase.BROWSER.equalsIgnoreCase("firefox")) {
            int x = webElement.getLocation().getX() + (webElement.getSize().getWidth() / 2);
            int y = webElement.getLocation().getY() + (webElement.getSize().getHeight() / 2);

            try {
                actions.moveByOffset(x, y).build().perform();
            } catch (MoveTargetOutOfBoundsException e) {
                scrollToElement(driver, webElement);
                actions.moveToElement(webElement).build().perform();
            }

            Thread.sleep(1000);
        }
    }

    public void scrollToElement(RemoteWebDriver driver, WebElement webElement) {
        javascriptExecutor(driver).executeScript("window.scrollTo(" + webElement.getLocation().x + "," + webElement.getLocation().y + ");");
    }
}
