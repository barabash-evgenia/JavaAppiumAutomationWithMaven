package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class SearchTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Search an article")
    @Description("Searching an article with title 'Object-oriented programming language' by search line 'Java'")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Cancel search")
    @Description("Click search input then click cancel button to hide search input")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check a few search results")
    @Description("Searching a few articles by search line 'Linkin Park Diskography'")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "Linkin Park Diskography";
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles(searchLine);
        Assert.assertTrue(
                "We found too few results!",
                amountOfSearchResults > 0);
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check a few search results")
    @Description("Getting an empty search result by search line 'kgfkgfkfkx'")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "kgfkgfkfkx";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Search then cancel search")
    @Description("Getting results by search line 'Java', checking there are at least 2 results, then click cancel button and check that any search result to disappear")
    @Step("Starting test testSearchAndCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchAndCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "Java";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForAnySearchResult();
        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles(searchLine);
        if (amountOfSearchResults <= 1) {
            Assert.fail("Search result contains less than two items");
        }
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.waitForAnySearchResultToDisappear();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Check search results by title and description")
    @Description("Getting results by search line 'Java', checking articles with title and description 'Java Island in Indonesia', 'Java (programming language) Object-oriented programming language', 'JavaScript High-level programming language'")
    @Step("Starting test testCheckSearchResultsByTitleAndDescription")
    public void testCheckSearchResultsByTitleAndDescription() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "Java";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForAnySearchResult();
        if (Platform.getInstance().isIOS() || Platform.getInstance().isMW()) {
            searchPageObject.waitForElementByTitleAndDescription("Java", "Island in Indonesia");
        } else {
            searchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia, Southeast Asia");
        }
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }
}
