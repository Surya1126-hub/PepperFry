Feature: Gift Card Validation
  As a user, I want to ensure the gift card form validates invalid data correctly.

  Scenario: Validate error messages for invalid gift card details
    Given I open the Pepperfry website
    When I open the Gift Cards page
    And I select the "Happy Birthday" gift card
    And I enter invalid gift card details
    Then I should capture and print the error message shown