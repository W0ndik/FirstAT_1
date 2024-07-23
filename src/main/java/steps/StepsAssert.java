package steps;

import helpers.Screenshoter;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.GoogleAfterSearch;
import pages.OpenAllCourse;

public class StepsAssert {

    @Step("Проверка в результатах поиска наличия имени: {title}")
    public static void checkContainsName(GoogleAfterSearch googleAfterSearch, String title){
        Screenshoter.getScreen(googleAfterSearch.getWebDriver());
        googleAfterSearch.checkingTitleByText(title);
    }

    @Step("Перейдём по ссылке с текстом {title}")
    public static void goPageText(GoogleAfterSearch googleAfterSearch, String title){
        googleAfterSearch.goPageByLinkName(title);
    }

    @Step("Проверка курса {moneyType}")
    public static void checkCourse(String moneyType, OpenAllCourse openAllCourse){
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
