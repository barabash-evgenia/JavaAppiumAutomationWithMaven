package lib.ui.ios;

import lib.ui.NavigationUIPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSNavigationUIPageObject extends NavigationUIPageObject {

    static {
        MY_LISTS_LINK = "xpath://XCUIElementTypeButton[@name='Saved']";
    }

    public IOSNavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
