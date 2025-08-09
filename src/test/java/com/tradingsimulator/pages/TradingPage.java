package com.tradingsimulator.pages;



import com.tradingsimulator.core.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TradingPage extends BasePage {

    private final By marketDropdown = By.cssSelector("button[data-test-id='pairs-select-input']");
    private final By marketSearch = By.xpath("//input[@data-test-id=\"pairs-search-input\"]");
    private final By pairRow = By.xpath("//tr[@data-test-id='pairs-table-row']//td[2]");

    private final By livePriceBadge = By.xpath("//body[1]/div[1]/main[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/p");

    private final By limitTab = By.xpath("//div[normalize-space()='Limit']");
    private final By buyTab = By.xpath("//button[normalize-space()='Buy']");
    private final By priceInput = By.xpath("(//input[@inputmode=\"decimal\"])[1]");
    private final By amountInput = By.xpath("(//input[@inputmode=\"decimal\"])[2]");
    private final By buyButton = By.xpath("(//button[contains(normalize-space(.), 'Buy')])[2]");
    private final By ordersTabOpen = By.xpath("//div[normalize-space()='Open orders']");
    private final By cancelAll = By.xpath("//span[normalize-space()='Cancel all orders']");
    private final By firstCancel = By.xpath("//button[normalize-space()='Cancel']");

    public TradingPage(WebDriver d){ super(d); }

    public void selectAsset(String assetPair){

        wait.clickable(marketDropdown, 20).click();
        WebElement input = wait.visible(marketSearch, 10);
        input.sendKeys(assetPair);
        wait.visible(pairRow, 10).click();
        wait.sleep(1200);
    }

    public double readLivePrice(){
        String txt = wait.visible(livePriceBadge, 20).getText();
        return PriceUtils.parsePrice(txt);
    }

    public void placeLimitBuy(double price, String amount){
        wait.clickable(limitTab, 10).click();
        wait.clickable(buyTab, 5).click();
        WebElement p = wait.visible(priceInput, 10);
        p.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE, String.valueOf(price));
        WebElement a = wait.visible(amountInput, 10);
        a.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE, amount);
        wait.clickable(buyButton, 10).click();
        wait.sleep(1000);
    }

    public void cancelExistingOrder(){
        try {
            wait.clickable(firstCancel, 5).click();
        } catch (Exception e){
            try { wait.clickable(cancelAll, 5).click(); } catch (Exception ignore) {}
        }
        wait.sleep(800);
    }
}
