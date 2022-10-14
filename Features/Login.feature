#Sample Feature Definition Template
@LoginPayTM
Feature: To Recharge in PayTM
  
  @Loginsuccess
  Scenario: To Recharge Post paid in PayTM
    Given I naviate to application
    When i click on recharge
    Then I select postpaid
    Then I polulate Mobilenumber,Operator,Amount
    And Proceed to Recharge		 
    
 