package lib.ui.mobile_web;

import lib.ui.NavigationUIPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUIPageObject extends NavigationUIPageObject {

    static {
        MY_LISTS_LINK = "css:a[data-event-name='menu.unStar']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
    }

    public MWNavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
