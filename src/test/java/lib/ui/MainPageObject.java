package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    
    protected RemoteWebDriver driver;
    
    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Step("Waiting for element presented on the page")
    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String errorMessage){
        return waitForElementPresent(locator, errorMessage, 5);
    }

    @Step("Waiting for element not presented on the page")
    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    @Step("Waiting for element presented on the page and clicking on it")
    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }


    @Step("Trying to click on element with few attempts. Method is for Mobile Web only")
    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;
        while (needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 1);
                }
            }
            ++currentAttempts;
        }
    }

    @Step("Scrolling web page. Method is for Mobile Web only")
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " +  Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Scrolling web page till element is not visible. Method is for Mobile Web only")
    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        WebElement element = this.waitForElementPresent(locator, errorMessage);
        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreadySwiped;
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    @Step("Waiting for element presented on the page and sending keys")
    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    @Step("Waiting for element presented on the page and clearing it")
    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    @Step("Waiting for element presented on the page and assert element has certain text")
    public WebElement assertElementHasText(String locator, String expectedElementText, String errorMessage){
        WebElement element = waitForElementPresent(locator, "Cannot find element");
        String actualElementText = element.getText();
        Assert.assertEquals(
                errorMessage,
                expectedElementText,
                actualElementText
        );
        return element;
    }

    @Step("Waiting for element presented on the page and assert element contains text")
    public WebElement assertElementContainsText(WebElement element, String expectedValue, String errorMessage){
        String actualElementText = element.getText();
        Assert.assertTrue(
                errorMessage,
                actualElementText.contains(expectedValue)
        );
        return element;
    }

    @Step("Getting list of elements")
    public List<WebElement> getListOfElements(String locator){
        By by = this.getLocatorByString(locator);
        return driver.findElements(by);
    }

    @Step("Swiping page up")
    public void swipeUp(int timeOfSwipe){
        if (driver instanceof RemoteWebDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);
            action
                    .press(PointOption.point(x, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, endY))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " +  Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swiping page up quick")
    public void swipeUpQuick(){
        swipeUp(200);
    }

    @Step("Swiping page up to find")
    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        By by = this.getLocatorByString(locator);
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }

    }

    @Step("Swiping page up till element presented on the page")
    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    @Step("Checking element is presented on the page")
    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = this.waitForElementPresent(locator, "Cannot find element by locator", 5).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            Object jsResult = executor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    @Step("Clicking element to the right corner. Method is for iOS only")
    public void clickElementToTheRightUpperCorner(String locator, String errorMessage){
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", errorMessage);
            int rightX = element.getLocation().getX();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;
            int width = element.getSize().getWidth();
            int pointToClickX = (rightX + width) - 3;
            int pointToClickY = middleY;
            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(PointOption.point(pointToClickX, pointToClickY)).release();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " +  Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swiping element to the left. Method is for Android only")
    public void swipeElementToLeft(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    errorMessage,
                    10);

            int leftX = element.getLocation().getX();
            int rightX = leftX + element.getSize().getWidth();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.press(PointOption.point(rightX, middleY));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
            if(Platform.getInstance().isAndroid()){
                action.moveTo(PointOption.point(leftX, middleY));
            } else {
                int offsetX = (-1 * element.getSize().getWidth());
                action.moveTo(PointOption.point(offsetX, 0));
            }
            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " +  Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Getting amount of elements")
    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }

    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void findArticlesBySearchLine(String searchLine){
        waitForElementAndClick(
                "xpath://*[contains(@text,'Search Wikipedia')]",
                "Cannot find search 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                "xpath://*[contains(@text,'Search…')]",
                searchLine,
                "Cannot find 'Search…' input",
                5
        );
    }

    public void addAnArticleToNewList(String articleTitle, String folderName){
        waitForElementAndClick(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articleTitle + "']",
                "Cannot find '" + articleTitle + "' topic",
                15
        );
        waitForElementPresent(
                "id:org.wikipedia:id/view_page_title_text",
                "Cannot find article title",
                15
        );
        waitForElementAndClick(
                "xpath://android.widget.ImageView[@content-desc='More options']",
                "Cannot find button to open article options",
                5
        );
        waitForElementAndClick(
                "xpath://android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[@text='Add to reading list']",
                "Cannot find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                "id:org.wikipedia:id/onboarding_button",
                "Cannot find 'Got it' tip overlay",
                5
        );
        waitForElementAndClear(
                "id:org.wikipedia:id/text_input",
                "Cannot find input to set name of articles folder",
                5
        );
        waitForElementAndSendKeys(
                "id:org.wikipedia:id/text_input",
                folderName,
                "Cannot put text into articles folder input",
                5
        );
        waitForElementAndClick(
                "xpath://*[@text='OK']",
                "Cannot press 'OK' button",
                5
        );
        waitForElementAndClick(
                "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void addAnArticleToExistingList(String articleTitle, String folderName){
        waitForElementAndClick(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articleTitle + "']",
                "Cannot find '" + articleTitle + "' topic",
                15
        );
        waitForElementPresent(
                "id:org.wikipedia:id/view_page_title_text",
                "Cannot find article title",
                15
        );
        waitForElementAndClick(
                "xpath://android.widget.ImageView[@content-desc='More options']",
                "Cannot find button to open article options",
                5
        );
        waitForElementAndClick(
                "xpath://android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[@text='Add to reading list']",
                "Cannot find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                "xpath://android.widget.TextView[@text='" + folderName + "']",
                "Cannot find an existing folder " + folderName,
                5
        );
        waitForElementAndClick(
                "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void assertElementPresent(String locator, String errorMessage) {
        By by = this.getLocatorByString(locator);
        String defaultMessage = "An element '" + locator + "' supposed to be present";
        if (driver.findElement(by).getText() == null) {
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
    
    private By getLocatorByString(String locatorWithType){
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];
        
        if (byType.equals("xpath")){
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else if (byType.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator " + locator);
        }
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public String takeScreenshot(String name) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) this.driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }

}
