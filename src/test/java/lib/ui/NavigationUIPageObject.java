package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUIPageObject extends MainPageObject{

    protected static String
            MY_LISTS_LINK,
            OPEN_NAVIGATION;

    public NavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Opening my lists")
    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5);
        } else {
            this.waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5);
        }
    }

    @Step("Opening navigation menu. Method is for Mobile Web only")
    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(
                    OPEN_NAVIGATION,
                    "Cannot find and click open navigation button",
                    5);
        } else {
            System.out.println("Method openNavigation() does nothing for platform " +  Platform.getInstance().getPlatformVar());

        }
    }


}
