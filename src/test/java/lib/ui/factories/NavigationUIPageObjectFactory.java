package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationUIPageObject;
import lib.ui.android.AndroidNavigationUIPageObject;
import lib.ui.ios.IOSNavigationUIPageObject;
import lib.ui.mobile_web.MWNavigationUIPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIPageObjectFactory {

    public static NavigationUIPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUIPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new IOSNavigationUIPageObject(driver);
        } else {
            return new MWNavigationUIPageObject(driver);
        }
    }
}
