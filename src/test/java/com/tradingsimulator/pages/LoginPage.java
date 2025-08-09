package com.tradingsimulator.pages;

import com.tradingsimulator.core.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private final By email = By.xpath("//input[@id='email']");
    private final By password = By.xpath("//input[@id='password']");
    private final By continueBtn = By.xpath("//button[@type='submit']");
    private final By tradingHeader = By.xpath("(//*[ contains(text(),'Order book')])[1]");

    public LoginPage(WebDriver d){ super(d); }

    public void open() {
        driver.get(ConfigReader.get("base.url"));
    }

    public void login(String user, String pass){
        wait.visible(email, 20).sendKeys(user);
        wait.visible(password, 10).sendKeys(pass);
        wait.clickable(continueBtn, 10).click();
        wait.visible(tradingHeader, 30);
    }
    private final By headerSettingsBtn = By.xpath("//button[@data-test-id='header-settings']");
    private final By demoToggleInput  = By.xpath("//input[@data-test-id='header-settings-demo']");
    private final By demoToggleKnob   = By.xpath("//input[@data-test-id='header-settings-demo']/following-sibling::div");
    private final By stayOnDemoBtn    = By.xpath("//button[contains(normalize-space(.), 'Stay on') and contains(., 'Demo')]");

    public void ensureDemoEnabled() {
        wait.clickable(headerSettingsBtn, 10).click();

        WebElement input = driver.findElement(demoToggleInput);
        boolean isChecked =
                "true".equalsIgnoreCase(input.getAttribute("checked")) ||
                        "true".equalsIgnoreCase(input.getAttribute("aria-checked"));

        if (!isChecked) {
            wait.clickable(demoToggleKnob, 10).click();

            try {
                wait.clickable(stayOnDemoBtn, 5).click();
            } catch (Exception ignore) {
            }

        }

    }

}
