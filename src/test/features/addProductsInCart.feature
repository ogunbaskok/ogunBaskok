@Scenario1
Feature: Automation Exercise

  Background: System Login
    Given Navigate to "https://automationexercise.com" url
    Then Verify that home page is visible successfully

  Scenario Outline: Add Products in Cart
    When Click <products> button
    Then Hover over <product1>
    And Click <addCart> button
    And Click <contShopping> button
    Then Hover over <products2>
    And Click <addCart2> button
    And Click <viewCart> button
    Then Verify both <verifyProducts1> and <verifyProducts2> are added to Cart
    Then Verify their <priceElement1> <priceElement2> <quantityElement1> <quantityElement2> and <totalElement1> <totalElement2>
    Examples:
      |  products   | product1    |  addCart    | contShopping       | products2   | addCart2     | viewCart   | verifyProducts1     | verifyProducts2     | priceElement1            | priceElement2            | quantityElement1           | quantityElement2           | totalElement1           | totalElement2           |
      |  "products" | "product_1" | "addToCart" | 'continueShopping' | "product_2" | "addToCart2" | 'viewCart' | "verify_products_1" | "verify_products_2" | "verify_product1_prices" | "verify_product2_prices" | "verify_product1_quantity" | "verify_product2_quantity" | "verify_product1_total" | "verify_product2_total" |
