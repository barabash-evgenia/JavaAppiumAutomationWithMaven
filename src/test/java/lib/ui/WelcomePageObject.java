package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String ELEMENT_TYPE_STATIC_TEXT = "xpath://XCUIElementTypeStaticText[@name='%s']",
                                ELEMENT_TYPE_BUTTON = "xpath://XCUIElementTypeButton[@name='%s']",
                                SKIP = "xpath://XCUIElementTypeButton[@name='Skip']";

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Learn more about Wikipedia"),
                "Cannot find 'Learn more about Wikipedia' link",
                10
        );
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "New ways to explore"),
                "Cannot find 'New ways to explore' text",
                10
        );
    }

    public void waitForAddOrEditPreferredLanguagesLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Add or edit preferred languages"),
                "Cannot find 'Add or edit preferred languages' link",
                10
        );
    }

    public void waitForLearnMoreAboutDataCollectedLink() {
        this.waitForElementPresent(
                String.format(ELEMENT_TYPE_STATIC_TEXT, "Learn more about data collected"),
                "Cannot find 'Learn more about data collected' link",
                10
        );
    }

    public void clickNextButton() {
        this.waitForElementAndClick(
                String.format(ELEMENT_TYPE_BUTTON, "Next"),
                "Cannot find and click 'Next' button",
                10
        );
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                String.format(ELEMENT_TYPE_BUTTON, "Get started"),
                "Cannot find and click 'Get started' button",
                10
        );
    }

    public void clickSkip() {
        this.waitForElementAndClick(
                SKIP,
                "Cannot find and click skip button",
                5);
    }
}
