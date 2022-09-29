Feature: User addDish

@success
Scenario: User login with valid admin credentials
  Given Open the chrome and login page
  Given User enter valid login and valid password
  Given User should be able to login admin successfully
  Given User click to the button "Add dish"
  When User filled dish attributes by valid values
  Then User click to the button "Save"
  Then User has been redirected to findAll page
  Then A new dish is displayed
