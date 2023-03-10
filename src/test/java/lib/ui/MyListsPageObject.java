package lib.ui;

import io.qameta.allure.Step;
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

    @Step("Opening my folder by name. Method is for Android only")
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

    @Step("Waiting for article to appear by its title")
    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        this.waitForElementPresent(
                articleTitleXpath,
                "Cannot find saved article still by title " + articleTitle,
                15
        );
    }

    @Step("Waiting for article to disappear by its title")
    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        this.waitForElementNotPresent(
                articleTitleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    @Step("Swiping article to delete it")
    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleTitleXpath = String.format(ARTICLE_BY_TITLE_TPL, articleTitle);
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleTitleXpath,
                    "Cannot find saved article"
            );
        } else {
            String removeButton = String.format(REMOVE_FROM_SAVED_BUTTON, articleTitle);
            this.waitForElementAndClick(
                    removeButton,
                    "Cannot click button to remove article from saved",
                    10);
            this.waitForElementNotPresent(
                    removeButton,
                    "Remove button is still presented",
                    10
                    );
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

    @Step("Closing 'Sync your saved articles?'. Method is for iOS only")
    public void closeSyncYourSavedArticlesWindow() {
        this.waitForElementAndClick(CLOSE_SYNC_YOUR_SAVED_ARTICLES_WINDOW,
                "Cannot find 'X' button to close 'Sync your saved articles?' window",
                5);
    }

    @Step("Clicking 'swipe action delete' button. Method is for iOS only")
    public void clickSwipeActionDeleteButton() {
        this.waitForElementAndClick(SWIPE_ACTION_DELETE_BUTTON,
                "Cannot find 'swipe action delete' button",
                5);
    }

}
