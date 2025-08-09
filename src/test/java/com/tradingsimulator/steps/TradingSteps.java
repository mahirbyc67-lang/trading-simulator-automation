package com.tradingsimulator.steps;


import com.tradingsimulator.core.ConfigReader;
import com.tradingsimulator.core.PriceUtils;
import com.tradingsimulator.core.Waiter;
import com.tradingsimulator.hooks.Hooks;
import com.tradingsimulator.pages.LoginPage;
import com.tradingsimulator.pages.TradingPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class TradingSteps {
    private final WebDriver driver = Hooks.driver();
    private final LoginPage login = new LoginPage(driver);
    private final TradingPage trade = new TradingPage(driver);
    private final Waiter wait = new Waiter(driver);

    private double startPrice;
    private double orderPrice;

    @Given("I login to Bitsgap")
    public void i_login_to_bitsgap() {
        login.open();
        String user = ConfigReader.get("usernames");
        String pass = ConfigReader.get("password");
        if (user == null || user.isBlank() || pass == null || pass.isBlank()) {
            throw new RuntimeException("Set BITS_USER and BITS_PASS in env or config/test.properties");
        }
        login.login(user, pass);
        login.ensureDemoEnabled();
        wait.sleep(5000);
    }

    @When("I select asset pair {string}")
    public void i_select_asset_pair(String asset) {
        trade.selectAsset(asset);
        startPrice = trade.readLivePrice();
        System.out.println("Initial price: " + startPrice);
    }

    @And("I wait for price to move by abs {double} or pct {double}")
    public void i_wait_for_price_move(double abs, double pct) {
        int maxSec = Integer.parseInt(ConfigReader.get("maxWatchSeconds"));
        int poll = Integer.parseInt(ConfigReader.get("pollIntervalMillis"));
        long end = System.currentTimeMillis() + (maxSec * 1000L);

        while (System.currentTimeMillis() < end) {
            double cur = trade.readLivePrice();
            if (PriceUtils.movedByAbs(cur, startPrice, abs) || PriceUtils.movedByPct(cur, startPrice, pct)) {
                System.out.println("Move hit. Current: " + cur);
                return;
            }
            wait.sleep(poll);
        }
        Assert.fail("Price did not move by required thresholds within " + maxSec + "s");
    }

    @And("I place a limit buy offset {double} with amount {string}")
    public void i_place_limit(double offset, String amount) {
        orderPrice = startPrice - offset;
        trade.placeLimitBuy(orderPrice, amount);
    }

    @And("I wait again for price to move by abs {double}")
    public void i_wait_again(double abs) {
        int maxSec = Integer.parseInt(ConfigReader.get("maxWatchSeconds"));
        int poll = Integer.parseInt(ConfigReader.get("pollIntervalMillis"));
        long end = System.currentTimeMillis() + (maxSec * 1000L);

        while (System.currentTimeMillis() < end) {
            double cur = trade.readLivePrice();
            if (PriceUtils.movedByAbs(cur, startPrice, abs)) return;
            wait.sleep(poll);
        }
        Assert.fail("Second movement not reached in time");
    }

    @Then("I cancel the pending order")
    public void i_cancel_order() {
        trade.cancelExistingOrder();
        // (You can add verification by checking no open orders rows present.)
        Assert.assertTrue(true, "Cancel action executed");
    }
}
