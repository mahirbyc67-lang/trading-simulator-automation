package com.tradingsimulator.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waiter {
    private final WebDriver driver;
    public Waiter(WebDriver d){ this.driver = d; }

    public WebElement visible(By by, int sec){
        return new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickable(By by, int sec){
        return new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public void sleep(long ms){ try { Thread.sleep(ms);} catch (InterruptedException ignored){} }
}
