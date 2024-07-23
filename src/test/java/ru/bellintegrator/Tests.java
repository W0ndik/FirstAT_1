package ru.bellintegrator;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.*;
import steps.StepsAssert;

import java.util.List;
import java.util.Map;

import static steps.StepsAll.*;

public class Tests extends BaseTests{

    @Feature("Проверка тайтла")
    @DisplayName("Проверка тайтла сайта")
    @Test
    public void firstTest(){
        chromeDriver.get("https://bellintegrator.ru/");
        String title = chromeDriver.getTitle();
        Assertions.assertTrue(title.contains("Bell Integrator"),"Тайтл "+title+" на сайте не содержит Bell Integrator");
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска напрямую")
    @Test
    public void testFind(){
        chromeDriver.get("https://bellintegrator.ru/search/");
        WebElement searchField = chromeDriver.findElement(By.xpath("//input[@id='edit-keys' and @type='search']"));
        WebElement searchButton = chromeDriver.findElement(By.xpath("//input[@id='edit-submit' and @value='Поиск']"));

        searchField.click();
        searchField.sendKeys("Филенков");
        searchButton.click();

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//ol[contains(@class,'search-results')]//p[contains(@class,'snippet')]"));
        System.out.println(resultSearch.size());
        System.out.println(resultSearch);
        resultSearch.forEach(x-> System.out.println(x.getText()));
        Assertions.assertTrue(resultSearch.stream().anyMatch(x->x.getText().contains("руководитель")),
                "Статьи содержащие руководитель не найдены для поискового слова Филенков");
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PO")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource({"Филенков,руководитель","Минаев,руководитель","Результаты проектов,плохо"})
    //MethodSource
    public void testPO(String word, String result){
        chromeDriver.get("https://bellintegrator.ru/search/");
        BellBeforeSearch bellBeforeSearch = new BellBeforeSearch(chromeDriver);
        bellBeforeSearch.find(word);
        BellAfterSearch bellAfterSearch = new BellAfterSearch(chromeDriver);
        Assertions.assertTrue(bellAfterSearch.getResults().stream().anyMatch(x->x.getText().contains(result)),
                "Статьи содержащие "+result+" не найдены для поискового слова "+word);
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PF")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource({"Филенков,руководитель"})
    public void testPF(String word, String result){
        chromeDriver.get("https://bellintegrator.ru/search/");
        BellPageFactory bellPageFactory = PageFactory.initElements(chromeDriver,BellPageFactory.class);
        bellPageFactory.find(word);
        Assertions.assertTrue(bellPageFactory.getResults().stream().anyMatch(x->x.getText().contains(result)),
                "Статьи содержащие "+result+" не найдены для поискового слова "+word);
    }

    @Feature("Проверка курса валют")
    @DisplayName("Проверка курса валют - базовая")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingMoney")
    public void OpenList(String searchQuery, String title, String money){
        chromeDriver.get("https://www.google.ru/");
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.find(searchQuery);
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        googleAfterSearch.goPageByLinkName(title);
        OpenMain openPage = new OpenMain(chromeDriver);
        openPage.goAllCource();
        OpenAllCourse openAllCourse = new OpenAllCourse(chromeDriver);
        List<Map<String,String>> collectExchangeRates = openAllCourse.getCollectExchangeRates();
        System.out.println(collectExchangeRates);
        System.out.println(
                collectExchangeRates.stream()
                        .filter(x->x.get("Валюта обмена").contains(money))
                        .findFirst()
                        .get().get("Банк покупает")
        );
        Assertions.assertTrue(
                Double.parseDouble(
                        collectExchangeRates.stream()
                                .filter(x->x.get("Валюта обмена").contains(money))
                                .findFirst()
                                .get().get("Банк покупает").replace(",",".")
                )
                        <
                        Double.parseDouble(
                                collectExchangeRates.stream()
                                        .filter(x->x.get("Валюта обмена").contains(money))
                                        .findFirst()
                                        .get().get("Банк продаёт").replace(",",".")
                        ),
                "Курс покупки для "+money+" больше курса продажи "+money
        );
    }

    @Feature("Проверка курса валют")
    @DisplayName("Проверка курса валют - всё в степах")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingMoney")
    public void testOpenV2WithStepsAll(String searchQuery, String title, String money) {
        openSite("https://www.google.ru/","Google",chromeDriver);
        searchInGoogle(searchQuery);
        validateSearchInGoogle(title);
        goSiteByLink(title);
        checkCourse(money);
    }

    @Feature("Проверка курса валют")
    @DisplayName("Проверка курса валют - проверки в степах")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingMoney")
    public void testOpenV2WithStepsAssert(String searchQuery, String title, String money) {
        chromeDriver.get("https://www.google.ru/");
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.find(searchQuery);
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        StepsAssert.checkContainsName(googleAfterSearch, title);
        StepsAssert.goPageText(googleAfterSearch, title);
        OpenMain openPage = new OpenMain( chromeDriver);
        openPage.goAllCource();
        OpenAllCourse openAllCourse = new OpenAllCourse(chromeDriver);
        openAllCourse.getCollectExchangeRates();
        StepsAssert.checkCourse(money,openAllCourse);
    }


















}
