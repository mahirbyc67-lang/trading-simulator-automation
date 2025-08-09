package com.tradingsimulator.pages;


import com.tradingsimulator.core.Waiter;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected final WebDriver driver;
    protected final Waiter wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Waiter(driver);
    }
}
