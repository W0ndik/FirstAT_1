package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> providerCheckingMoney(){
        return Stream.of(
                Arguments.of("Oткрытие", "Банк Открытие: кредит наличными — под 4% каждому ...", "USD")//,
                //Arguments.of("Oткрытие", "Банк Открытие: кредит наличными — под 4% каждому", "EUR")
        );
    }


    public static Stream<Arguments> provideCheckingMoneyList(){
        List<String> money = new ArrayList<>();
        money.add("USD");
        money.add("EUR");
        return Stream.of(
                Arguments.of("Oткрытие", "Банк Открытие: кредит наличными — под 4% каждому ...", money)
        );
    }
}
