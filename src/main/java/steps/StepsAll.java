package steps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleAfterSearch;
import pages.GoogleBeforeSearch;
import pages.OpenAllCourse;
import pages.OpenMain;

public class StepsAll {
    private static WebDriverWait wait;
    private static WebDriver driver;

    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, String title, WebDriver currentDriver){
        driver=currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.titleIs(title));
    }

    @Step("Проверяем наличие тайтла: {title} в результатах поиска яндекса")
    public static void validateSearchInGoogle(String title){
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(driver);
        googleAfterSearch.checkingTitleByText(title);
    }

    @Step("Ищем в поисковике по ключевой фразе: {searchQuery}")
    public static void searchInGoogle(String searchQuery){
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(driver);
        googleBeforeSearch.find(searchQuery);
    }

    @Step("Переходим на сайт по заголовку: {title}")
    public static void goSiteByLink(String title){
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(driver);
        googleAfterSearch.goPageByLinkName(title);
    }

    @Step("Проверка курса {moneyType}")
    public static void checkCourse( String moneyType){
        OpenMain openPage = new OpenMain(driver);
        openPage.goAllCource();
        OpenAllCourse openAllCourse = new OpenAllCourse(driver);
        Assertions.assertTrue(
                Double.parseDouble(
                        openAllCourse.getCollectExchangeRates().stream()
                                .filter(x->x.get("Валюта обмена").contains(moneyType))
                                .findFirst()
                                .get().get("Банк покупает").replace(",","."))
                        <
                        Double.parseDouble(
                                openAllCourse.getCollectExchangeRates().stream()
                                        .filter(x->x.get("Валюта обмена").contains(moneyType))
                                        .findFirst()
                                        .get().get("Банк продаёт").replace(",","."))
        );
    }



}
