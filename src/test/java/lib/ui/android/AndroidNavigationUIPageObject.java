package lib.ui.android;

import lib.ui.NavigationUIPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUIPageObject extends NavigationUIPageObject {

    static {
        MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }

    public AndroidNavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
