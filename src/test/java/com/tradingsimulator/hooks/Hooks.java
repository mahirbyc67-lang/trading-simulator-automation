package com.tradingsimulator.hooks;



import com.tradingsimulator.core.ConfigReader;
import com.tradingsimulator.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    @Before
    public void setup(){
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        DriverFactory.initDriver(headless);
    }

    @After
    public void tearDown(){
        DriverFactory.quitDriver();
    }

    public static WebDriver driver(){
        return com.tradingsimulator.core.DriverFactory.getDriver();
    }
}
