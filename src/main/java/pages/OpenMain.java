package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class OpenMain {
    private String buttonClose = "//span[text()='Закрыть']/ancestor::button";

    private String allCourse="//a[text()='Все курсы']";

    private WebDriver driver;
    private WebDriverWait wait;

    public OpenMain(WebDriver driver){
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
        wait.until(visibilityOfElementLocated(By.xpath(buttonClose)));
        this.driver.findElement(By.xpath(buttonClose)).click();
    }

    public void goAllCource(){
        wait.until(visibilityOfElementLocated(By.xpath(allCourse)));
        driver.findElement(By.xpath(allCourse)).click();
    }
}
