Feature: Bookshelf Search and Filtering
  As a user looking for furniture, I want to filter bookshelves by price and material.

  Scenario: Filter and select bookshelves
    Given I open the Pepperfry website
    When I hover on "Furniture" and select "Bookshelves"
    And I filter bookshelves under "15000"
    And I apply material filter "Engineered Wood"
    Then I select the first 3 bookshelf products