package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class OpenAllCourse {

    private String selectorExchangeRates = "//section[not(@style='display: none;')]//table[@class='card-rates-table cards']";

    private String selectorTableHeaders=".//thead//th";

    private String selectorTableRows = ".//tbody/tr";

    private WebDriver driver;

    private WebDriverWait wait;
    private WebElement exchangeRates;
    private List<Map<String,String>> collectExchangeRates = new ArrayList<>();

    public OpenAllCourse(WebDriver driver){
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public List<Map<String, String>> getCollectExchangeRates() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorExchangeRates)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorTableHeaders)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorTableRows)));
        exchangeRates=driver.findElement(By.xpath(selectorExchangeRates));
        List<WebElement> tableHeaders = exchangeRates.findElements(By.xpath(selectorTableHeaders));
        List<WebElement> tableRows = exchangeRates.findElements(By.xpath(selectorTableRows));
        for(int i=0; i<tableRows.size();++i){
            Map<String,String> collectRow = new HashMap<>();
            for (int j=0; j<tableHeaders.size();++j){
                collectRow.put(
                        tableHeaders.get(j).getText(),
                        tableRows.get(i).findElement(By.xpath("./td["+(j+1)+"]")).getText()
                );
            }
            collectExchangeRates.add(collectRow);
        }
        return collectExchangeRates;
    }
}

























