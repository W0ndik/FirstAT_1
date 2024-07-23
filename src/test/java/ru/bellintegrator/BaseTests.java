package ru.bellintegrator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class BaseTests {

    protected WebDriver chromeDriver;

    @BeforeEach
    public void befor(){
        System.setProperty("webdriver.chrome.driver","C:\\tmp\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver",System.getenv("CHROME_DRIVER"));
        //chromeDriver = new ChromeDriver();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
        chromeDriver= new ChromeDriver(capabilities);
        chromeDriver.manage().window().maximize();
        //chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        //chromeDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void after(){
        chromeDriver.quit();
    }

}
