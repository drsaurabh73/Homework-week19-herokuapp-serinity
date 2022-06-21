
Feature: Testing different products on the Best Api Application

  Scenario: Check if the products application is accessed by the users
    Given I am on Homepage of application herokuapp
    When User send a GET Request to list endpoint booking
    Then User can get back a valid status code 200 of booking

  Scenario: Check if User can add  products in the application
    Given I am on Homepage of application herokuapp
    When User can create new booking using POST method in booking application
    Then User can get back a valid status code 200 of booking

  Scenario: Check if User can update  products in the application
    Given I am on Homepage of application herokuapp
    When User can update new booking using PUT method in booking application
    Then User can get back a valid status code 200 of booking

  Scenario: Check if User can Delete  products in the application
    Given I am on Homepage of application herokuapp
    When User can Delete new booking using DELETE method in booking application
    Then User can get back a valid status code 201 of booking
    And User verify that the product is deleted successfully from product

