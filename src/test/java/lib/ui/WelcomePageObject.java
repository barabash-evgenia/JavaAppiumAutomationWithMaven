package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String ELEMENT_TYPE_STATIC_TEXT = "xpath://XCUIElementTypeStaticText[@name='%s']",
                                ELEMENT_TYPE_BUTTON = "xpath://XCUIElementTypeButton[@name='%s']",
                                SKIP = "xpath://XCUIElementTypeButton[@name='Skip']";

    @Step("Waiting for 'Learn more about Wikipedia' link. Method is for iOS only")
    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Learn more about Wikipedia"),
                "Cannot find 'Learn more about Wikipedia' link",
                10
        );
    }

    @Step("Waiting for 'New ways to explore' label. Method is for iOS only")
    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "New ways to explore"),
                "Cannot find 'New ways to explore' text",
                10
        );
    }

    @Step("Waiting for 'Add or edit preferred languages' link. Method is for iOS only")
    public void waitForAddOrEditPreferredLanguagesLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Add or edit preferred languages"),
                "Cannot find 'Add or edit preferred languages' link",
                10
        );
    }

    @Step("Waiting for 'Learn more about data collected' link. Method is for iOS only")
    public void waitForLearnMoreAboutDataCollectedLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Learn more about data collected"),
                "Cannot find 'Learn more about data collected' link",
                10
        );
    }

    @Step("Clicking next button. Method is for iOS only")
    public void clickNextButton() {
        this.waitForElementAndClick(
                String.format(ELEMENT_TYPE_BUTTON, "Next"),
                "Cannot find and click 'Next' button",
                10
        );
    }

    @Step("Clicking get started button. Method is for iOS only")
    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                String.format(ELEMENT_TYPE_BUTTON, "Get started"),
                "Cannot find and click 'Get started' button",
                10
        );
    }

    @Step("Clicking skip button. Method is for iOS only")
    public void clickSkip() {
        this.waitForElementAndClick(
                SKIP,
                "Cannot find and click skip button",
                5);
    }
}
