package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String nameOfFolder = "Learning programming";
    private static final String login = "learnqa_barabash",
                                password = "Qwerty1324";


    @Test
    public void testSaveFirstArticleToMyList() {
        String currentUrl = "https://ru.wikipedia.org/";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isMW()) {
            currentUrl = driver.getCurrentUrl();
        }
        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToNewList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();
            driver.get(currentUrl);
            articlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()){
            searchPageObject.clickCancelButton();
        }
        NavigationUIPageObject navigationUI = NavigationUIPageObjectFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(nameOfFolder);
        } else if (Platform.getInstance().isIOS()) {
            myListsPageObject.closeSyncYourSavedArticlesWindow();
        }
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOneFromMyList() {
        String searchLine = "Java";
        String nameOfFolder = "Learning programming";
        String firstArticleTitle = "High-level programming language";
        String secondArticleTitle = "Object-oriented programming language";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUIPageObject navigationUI = NavigationUIPageObjectFactory.get(driver);;
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(firstArticleTitle);
        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToNewList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isAndroid()){
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine(searchLine);
        }
        searchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToExistingList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()){
            searchPageObject.clickCancelButton();
        }
        navigationUI.clickMyLists();
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(nameOfFolder);
        } else {
            myListsPageObject.closeSyncYourSavedArticlesWindow();
        }
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.waitForArticleToAppearByTitle(firstArticleTitle.toLowerCase());
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle.toLowerCase());
            myListsPageObject.swipeByArticleToDelete(firstArticleTitle.toLowerCase());
            myListsPageObject.waitForArticleToDisappearByTitle(firstArticleTitle.toLowerCase());
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle.toLowerCase());
        } else {
            myListsPageObject.waitForArticleToAppearByTitle(firstArticleTitle);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
            myListsPageObject.swipeByArticleToDelete(firstArticleTitle);
            myListsPageObject.waitForArticleToDisappearByTitle(firstArticleTitle);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
        }
    }
}
