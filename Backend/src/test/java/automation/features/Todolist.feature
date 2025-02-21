Feature: ToDoList Api Automation

  Scenario: Verify the get api of todolist
    Given I have the url of get api endpoint
    When I hit the url of api
    Then I receive the response code as 200 


  Scenario Outline: Verify the get api using valid id
    Given I have a task exists with Id
    When I retrieve the task using <Id>
    Then I receive the response code as 200 
    And the retrieved task should have a valid task description and status

    Examples:
        | Id                                    | 
        | 86315316-fe50-41d6-9f75-573365710368  | 


  Scenario Outline: Verify the get api using invalid id
    Given I have a task exists with Id
    When I retrieve the task using <Id>
    Then I receive the response code as 404

    Examples:
        | Id                                    | 
        | 81234316-fe50-41d6-9f75-573365710368  | 


  Scenario Outline: Verify the add task to list api
    Given I have a task to add with <tasktobedone> and <completed>
    When I click post api with task
    Then I receive the response code as 202
    And  I should receive added task

    Examples:
        | tasktobedone         | completed |
        | Complete Assignment  | false     |
        | Complete Quiz        | false     |


  Scenario Outline: Verify the update task to list
    Given I have a task to update with <tasktobedone> and <completed>
    When I click put api with task and <id>
    Then I receive the response code as 200
    And  I should receive updated task <tasktobedone> and <completed>

    Examples:
        | tasktobedone          | completed | id                                  |
        | Completed Assignment  | true      | 86315316-fe50-41d6-9f75-573365710368|


  Scenario Outline: Verify non existing task to update
    Given I have a task to update with <tasktobedone> and <completed>
    When I click put api with task and <id>
    Then I receive the response code as 404

    Examples:
        | tasktobedone          | completed | id                                  |
        | Completed Assignment  | true      | 81234316-fe50-41d6-9f75-573365710368|


  Scenario Outline: Verify delete task api
    Given I have a task to delete
    When I click delete api with <id>
    Then I receive the response code as 204
    And  I should see and empty object

  Examples:
        | id                                  |
        | 86315316-fe50-41d6-9f75-573365710368|

  Scenario Outline: Verify delete non existing task
    Given I have a task to delete
    When I click delete api with <id>
    Then I receive the response code as 404

  Examples:
        | id                                  |
        | 81234316-fe50-41d6-9f75-573365710368|



    