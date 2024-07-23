package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class GoogleBeforeSearch {

    private WebElement inputSearch;
    private WebElement buttonSearch;
    private WebDriver driver;

    private WebDriverWait wait;

    public GoogleBeforeSearch(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
        wait.until(visibilityOfElementLocated(By.xpath("//textarea[@title='Поиск']")));
        this.inputSearch = driver.findElement(By.xpath("//textarea[@title='Поиск']"));
        this.buttonSearch = driver.findElement(By.xpath("//div[not (@jsname)]/center/*[@value='Поиск в Google']"));
    }

    @Step("Поиск в Google по слову {text}")
    public void find(String text){
        inputSearch.sendKeys(text);
        driver.findElement(By.xpath("//img[@alt='Google']")).click();
        buttonSearch.click();
    }
}
