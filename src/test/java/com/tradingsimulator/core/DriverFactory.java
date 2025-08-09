package com.tradingsimulator.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return TL.get();
    }

    public static void initDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--start-maximized", "--disable-gpu", "--remote-allow-origins=*");
        TL.set(new org.openqa.selenium.chrome.ChromeDriver(options));
    }

    public static void quitDriver() {
        if (TL.get() != null) {
            TL.get().quit();
            TL.remove();
        }
    }
}
