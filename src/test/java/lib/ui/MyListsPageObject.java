package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_SYNC_YOUR_SAVED_ARTICLES_WINDOW,
            SWIPE_ACTION_DELETE_BUTTON,
            REMOVE_FROM_SAVED_BUTTON;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = String.format(FOLDER_BY_NAME_TPL, nameOfFolder);
        this.waitForElementPresent(
                folderNameXpath,
                "Cannot find folder by name" + nameOfFolder,
                5
        );
        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name" + nameOfFolder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        this.waitForElementPresent(
                articleTitleXpath,
                "Cannot find saved article still by title " + articleTitle,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        this.waitForElementNotPresent(
                articleTitleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }
    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleTitleXpath,
                    "Cannot find saved article"
            );
        } else {
            this.waitForElementAndClick(
                    articleTitleXpath,
                    "Cannot click button to remove article from saved",
                    10);
        }

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(articleTitleXpath, "Cannot find saved article");
            this.clickSwipeActionDeleteButton();
        }
        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void closeSyncYourSavedArticlesWindow() {
        this.waitForElementAndClick(CLOSE_SYNC_YOUR_SAVED_ARTICLES_WINDOW,
                "Cannot find 'X' button to close 'Sync your saved articles?' window",
                5);
    }

    public void clickSwipeActionDeleteButton() {
        this.waitForElementAndClick(SWIPE_ACTION_DELETE_BUTTON,
                "Cannot find 'swipe action delete' button",
                5);
    }

}
