Feature: User login

@success
Scenario: User login wit valid credentials
Given Open the chrome and login page
When User enter valid login and valid password
Then User should be able to login successfully
