package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "xpath://div[@class='header-action']/button[contains(@class,'cancel')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'%s')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://li[@title='%s']//div[text()='%s']";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.with-results";
        ANY_SEARCH_LIST_RESULT = "css:p.with-results";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
