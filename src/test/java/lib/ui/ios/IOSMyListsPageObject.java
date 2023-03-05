package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[@name='%s']/..";
        CLOSE_SYNC_YOUR_SAVED_ARTICLES_WINDOW = "xpath://XCUIElementTypeButton[@name='Close']";
        SWIPE_ACTION_DELETE_BUTTON = "xpath://XCUIElementTypeButton[@name='swipe action delete']";
    }

    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
