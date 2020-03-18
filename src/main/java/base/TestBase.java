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
        System.out.println(webElement.getLocation().getX() + (webElement.getSize().getWidth() / 2));
        System.out.println(webElement.getLocation().getY() + (webElement.getSize().getHeight() / 2));
        try {
            scrollToOfset(driver, webElement.getLocation().x, webElement.getLocation().y);
            Thread.sleep(500);
            actions.moveToElement(webElement).build().perform();
        } catch (MoveTargetOutOfBoundsException me) {
            scrollToElement(driver, webElement);
            Thread.sleep(500);
            actions.moveByOffset(webElement.getLocation().x, webElement.getLocation().y).build().perform();
        }
        Thread.sleep(1000);
    }

    public void scrollToElement(RemoteWebDriver driver, WebElement webElement) {
        javascriptExecutor(driver).executeScript("window.scrollTo(" + webElement.getLocation().x + "," + webElement.getLocation().y + ");");
    }

    public void scrollToOfset(RemoteWebDriver driver, int x, int y) {
        javascriptExecutor(driver).executeScript("window.scrollTo(" + x + "," + y + ");");
    }

    public static void waitForDOMLoad(RemoteWebDriver driver) {
        try {
            JavascriptExecutor js = driver;
            Boolean readyState;
            Boolean jqueryDefined;
            for (int i = 0; i <= 60; i++) {
                readyState = js.executeScript("return document.readyState").toString() != "complete";
                jqueryDefined = js.executeScript("return typeof jQuery").toString() != "function";

                if (readyState && jqueryDefined) {
                    break;
                } else {
                    Thread.sleep(100);
                }
            }

        } catch (Exception e) {
            try {
                throw e;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
