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
    private static String currentUrl = "https://ru.wikipedia.org/";


    @Test
    public void testSaveFirstArticleToMyList() {
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
        String firstArticleDescription = "High-level programming language";
        String secondArticleDescription = "Object-oriented programming language";
        String firstArticleTitle = "JavaScript";
        String secondArticleTitle = "Java (programming language)";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUIPageObject navigationUI = NavigationUIPageObjectFactory.get(driver);;
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(firstArticleDescription);
        articlePageObject.waitForTitleElement();
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
                    firstArticleTitle,
                    articlePageObject.getArticleTitle());
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine(searchLine);
        }
        searchPageObject.clickByArticleWithSubstring(secondArticleDescription);
        articlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToExistingList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()){
            searchPageObject.clickCancelButton();
        }
        navigationUI.openNavigation();
        navigationUI.clickMyLists();
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(nameOfFolder);
        } else if (Platform.getInstance().isIOS()) {
            myListsPageObject.closeSyncYourSavedArticlesWindow();
        }
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.waitForArticleToAppearByTitle(firstArticleDescription.toLowerCase());
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleDescription.toLowerCase());
            myListsPageObject.swipeByArticleToDelete(firstArticleDescription.toLowerCase());
            myListsPageObject.waitForArticleToDisappearByTitle(firstArticleDescription.toLowerCase());
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleDescription.toLowerCase());
        } else if (Platform.getInstance().isIOS()){
            myListsPageObject.waitForArticleToAppearByTitle(firstArticleDescription);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleDescription);
            myListsPageObject.swipeByArticleToDelete(firstArticleDescription);
            myListsPageObject.waitForArticleToDisappearByTitle(firstArticleDescription);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleDescription);
        } else {
            myListsPageObject.waitForArticleToAppearByTitle(firstArticleTitle);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
            myListsPageObject.swipeByArticleToDelete(firstArticleTitle);
            myListsPageObject.waitForArticleToDisappearByTitle(firstArticleTitle);
            myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
        }
    }
}
