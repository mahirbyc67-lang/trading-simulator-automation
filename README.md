# UI‑based Crypto Trading Simulation

This repository contains a **Java/Maven** solution to the automated trading challenge described by a recruiter.  
It demonstrates how to build a flexible UI‑driven trading simulator using **Selenium WebDriver** and **Cucumber BDD**.  
The goal is to monitor price movements on a crypto exchange, prepare a limit order when a predefined threshold is exceeded and cancel the order if the market reverses before it is filled. 
All interactions are performed through the exchange’s web interface rather than direct API calls.

## Why use a demo account?

Demo or paper‑trading accounts allow traders to experiment with strategies without risking real money.  Platforms such as Bitsgap provide live market data and simulate the actual order book 
so you can place orders under realistic conditions but with virtual funds.  According to Bitsgap’s documentation, their demo mode lets you trade “in real‑time while using the virtual account,
which has all the same market conditions as real trading on the platform”【149163301465246†L70-L76】.  In other words, you get a live market experience but avoid the painful lessons of losing money
【149163301465246†L106-L114】.  The simulator in this repository stops just short of clicking the final **Buy** or **Sell** button to ensure no real trades are executed.  
You should only enable the final click on a demo account.

## Project structure

```text
trading-simulator/
├── pom.xml                         # Maven build configuration
├── README.md                       # This guide
├── src/main/java/com/example/trading-simulator/
│   ├── Config.java                 # Encapsulates simulation parameters (asset, thresholds, duration)
│   ├── ExchangePage.java           # Page Object modelling common exchange UI actions
│   └── TradingSimulation.java      # Core simulation logic (initialises driver, monitors prices)
└── src/test/
    ├── java/com/example/tradingsim/
    │   ├── TradingSimulationSteps.java  # Cucumber step definitions
    │   └── TestRunner.java              # JUnit runner for Cucumber
    └── resources/features/
        └── trading_simulation.feature   # Feature file describing the BDD scenario
```

The **Page Object** (`ExchangePage`) encapsulates all the locators and actions for your chosen exchange.  The provided selectors are placeholders; inspect your exchange’s HTML using browser developer tools and update the `lastPriceLocator`, `limitPriceInputLocator`, `quantityInputLocator`, etc.  The simulation deliberately prints a message instead of clicking the final order and cancel buttons.  When you’re confident you are using a risk‑free demo account, uncomment those lines in `ExchangePage`.

## Running the simulation

This project uses Maven to manage dependencies and **WebDriverManager** to download the appropriate ChromeDriver binary.  To run the example scenarios:

1. Ensure you have **Java 11** installed.
2. Clone this repository and navigate into the `trading-simulator` directory.
3. Update the CSS selectors in `ExchangePage.java` to match your exchange’s UI.  Many exchanges use similar patterns for price displays and order forms, but you will need to inspect the page to get precise locators.
4. (Optional) Modify the examples in `trading_simulation.feature` to use different assets or thresholds.
5. Execute the Cucumber tests with Maven:

   ```sh
   mvn test
   ```

The test runner will launch a Chrome browser, navigate to the trading page for each asset and start monitoring the price.  When the price moves by the specified threshold (e.g. $25 for BTC/USDT), it enters the limit price (initial price minus the offset) and prints a message indicating where a real click would occur.  After another price movement (half the threshold by default) it prints a message indicating that it would cancel the order.  The entire simulation times out after three minutes to prevent endless polling.

## Behaviour‑Driven Development (BDD)

The feature file `trading_simulation.feature` defines the behaviour in plain English using the Given/When/Then syntax.  Cucumber binds those steps to Java code in `TradingSimulationSteps.java` and passes the parameters to a new `Config` object.  You can easily add more examples to the scenario outline for additional assets like **XRP/USDT**, **ADA/USDT**, etc.  The design intentionally separates test data (the feature file) from implementation details (the Java code).

## Flexibility and next steps

* **Multiple assets** – Add rows to the examples table to exercise the simulation against different trading pairs.  The `symbol` in `Config` is derived from the asset string (e.g. `"BTC/USDT"` becomes `BTC_USDT`).
* **Parameterisation** – The thresholds and offsets are passed through Cucumber parameters.  You can convert them to percentages or fractions if desired.
* **Order confirmation** – Once you have a demo account on an exchange such as Bitsgap that simulates real markets【149163301465246†L70-L76】, uncomment the calls to `clickPlaceOrder()` and `cancelOrder()` in `ExchangePage` to actually submit and cancel orders.  Never run these actions against a real account.
* **Enhanced assertions** – This example prints status to the console.  In a real test you might assert that the order appears in the Open Orders table, verify that it is cancelled, or record the order ID for further checks.
* **Browser choice** – Change the WebDriver initialisation in `TradingSimulation.init()` if you prefer Firefox or other browsers.
