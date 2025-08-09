@ui
Feature: Automated exchange trading simulation via UI

  # Default values can be overridden per Example row.
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
      | asset     | absMove | pctMove | limitOffset | cancelAbs | amount   |
      | ETH/USDT  | 0.15    | 0.15    | 2.00        | 1.00      | 0.030    |
      | BTC/USDT  | 15      | 0.10    | 100         | 10        | 0.0010   |
      | XRP/USDT  | 0.003   | 0.10    | 0.010       | 0.001     | 70      |

