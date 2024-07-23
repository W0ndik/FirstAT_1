package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class GoogleAfterSearch {

    private WebDriver driver;

    private WebDriverWait wait;

    public WebDriver getWebDriver() {
        return driver;
    }

    public GoogleAfterSearch(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public GoogleAfterSearch(WebDriver driver, String searchQuery) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
        driver.get("https://www.google.ru/search?q="+searchQuery);
    }

    public void checkingTitleByText(String link){
        wait.until(visibilityOfElementLocated(By.xpath("//*[text()='Результаты поиска']/following-sibling::*/*//div[@data-sokoban-container or @class='g']//h3[contains(.,'"+link+"')]")));
        Assertions.assertFalse( driver.findElements(By.xpath("//*[text()='Результаты поиска']/following-sibling::*/*//div[@data-sokoban-container or @class='g']//h3[contains(.,'"+link+"')]")).size()==0,
                "Не найдено тайтла с текстом: '"+link);
    }

    public void goPageByLinkName(String link){
        wait.until(visibilityOfElementLocated(By.xpath("//*[text()='Результаты поиска']/following-sibling::*/*//div[@data-sokoban-container or @class='g']//h3[contains(.,'"+link+"')]")));
        driver.findElement(By.xpath("//*[text()='Результаты поиска']/following-sibling::*/*//div[@data-sokoban-container or @class='g']//h3[contains(.,'"+link+"')]")).click();
        Set<String> tabs = driver.getWindowHandles();
        for(String tab:tabs)
            driver.switchTo().window(tab);
    }

}
