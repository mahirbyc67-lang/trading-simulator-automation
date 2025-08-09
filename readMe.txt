Bitsgap Trading Simulation - UI Automation (BDD with Java, Maven, TestNG, Cucumber)
This project automates a trading simulation on Bitsgap using:

Java 17

Maven

Selenium WebDriver

TestNG

Cucumber (BDD)

It monitors asset price movement, places/cancels limit orders based on thresholds, and supports multiple asset runs with configurable parameters.

📂 Project Structure

bitsgap-trading-bdd/
├─ pom.xml                       # Maven dependencies & build config
├─ testng.xml                    # TestNG suite runner
├─ README.md                     # This file
├─ /src
│  ├─ /test
│  │   ├─ /java/com/trading-simulator
│  │   │   ├─ core/              # DriverFactory, ConfigReader, Waiter, PriceUtils
│  │   │   ├─ pages/             # Page Object classes (LoginPage, TradingPage, etc.)
│  │   │   ├─ steps/             # Step Definitions (BDD steps)
│  │   │   ├─ hooks/             # Before/After hooks for driver lifecycle
│  │   │   └─ runner/            # TestRunner with @CucumberOptions
│  │   └─ /resources
│  │       ├─ features/          # Cucumber .feature files
│  │       └─ config/            # test.properties (default config)
└─ /reports                      # Generated reports (HTML, JSON)
⚙️ Prerequisites
Java 17 or higher

Maven 3.9+

Google Chrome (latest)

Stable internet connection

📦 Install & Setup
Clone the repository:


git clone <repo-url>
cd bitsgap-trading-bdd
Copy the example env file and set your Bitsgap login:

cp .env.example .env
Edit .env:

BITS_USER=your_email@example.com
BITS_PASS=your_password
(Or set them as system environment variables.)

(IMPORTANT:
 For security reasons, credentials are not included.
 Please open config/test.properties and add your Bitsgap username and password
 before running the tests.)

(Optional) Adjust src/test/resources/config/test.properties for:

Default asset, thresholds, polling interval, and timeouts

Browser type & headless mode

▶️ Running Tests
Run with Maven:

bash
Copy
Edit
mvn clean test
Filter by Cucumber tag (default: @ui):


mvn clean test -Dcucumber.filter.tags="@smoke"
📄 Feature File Example
src/test/resources/features/trading_simulation.feature:

gherkin
Copy
Edit
@ui
Feature: Automated exchange trading simulation via UI

  Background:
    Given I login to Bitsgap

  @smoke
  Scenario Outline: Watch price → place limit → watch again → cancel
    When I select asset pair "<asset>"
    And I wait for price to move by abs <absMove> or pct <pctMove>
    And I place a limit buy offset <limitOffset> with amount "<amount>"
    And I wait again for price to move by abs <cancelAbs>
    Then I cancel the pending order

    Examples:
      | asset     | absMove | pctMove | limitOffset | cancelAbs | amount  |
      | BTC/USDT  | 25      | 0.15    | 200         | 20        | 0.0010  |
      | ETH/USDT  | 0.15    | 0.15    | 2.00        | 1.00      | 0.070   |
      | XRP/USDT  | 0.005   | 0.15    | 0.015       | 0.003     | 110     |
📊 Reports
After a run:

Cucumber HTML: reports/cucumber.html

Cucumber JSON: reports/cucumber.json

Console log: price moves, actions, waits

🔍 Key Features
Multi-asset support via Cucumber Examples table

Price move watcher with both absolute and percentage thresholds

Order placement & cancellation via UI automation

Demo mode enforcement after login (auto-enables if off)

Configurable parameters in test.properties or overridden in the feature file

Resilient locators to handle UI changes