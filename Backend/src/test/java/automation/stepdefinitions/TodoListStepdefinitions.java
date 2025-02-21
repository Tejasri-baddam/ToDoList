package automation.stepdefinitions;

import io.cucumber.java.en.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TodoListStepdefinitions {

    private Response response;
    private final String BASE_URL = "http://localhost:8080/todolist";
    private RequestSpecification request;

    public TodoListStepdefinitions() {
        RestAssured.baseURI = BASE_URL;
        request = given().header("Content-Type", "application/json");
    }

    //Get Api testing
    @Given("I have the url of get api endpoint")
    public void i_have_the_url_of_get_api_endpoint(){
       request.when();
    }

    @When("I hit the url of api")
    public void i_hit_the_url_of_api() {
        response = request.get();
    }

    @Then("I receive the response code as {}")
    public void i_receive_the_response_code_as(int statusCode) {
        response.then().statusCode(statusCode);
    }


    //common given for update,get,delete scenarios
    @Given("I have a task exists with Id")
    public void i_have_a_task_exists_with_Id() {
        request.when();
    }

    //Get Api using Id
    @When("I retrieve the task using {}")
    public void I_retrieve_the_task_using(String id) {
        response = request.when().get("/{id}", id);
    }

    @Then("the retrieved task should have a valid task description and status")
    public void the_retrieved_task_should_have_a_valid_task_description_and_status() {
        response.then().body("taskToBeDone", notNullValue()).body("completed", notNullValue());
    }

    
    //Add task api testing
    @Given("I have a task to add with {} and {}")
    public void i_have_a_task_to_add(String taskToBeDone,String completed) {
        request = request.body(String.format("{\"taskToBeDone\": \"%s\", \"completed\": %s }", taskToBeDone, completed));
    }

    @When("I click post api with task")
    public void i_click_post_api_with_task() {
        response = request.when().post();
    }

    @Then("I should receive added task")
    public void i_should_receive_added_task() {
        response.then().body("id", notNullValue());
    }

    //update api testing
    @Given("I have a task to update with {} and {}")
    public void i_have_a_task_to_update_with_and(String updateTask,String completed){
        request = request.body(String.format("{\"taskToBeDone\": \"%s\", \"completed\": %s }", updateTask, completed));
    }

    @When("I click put api with task and {}")
    public void i_click_put_api_with_task_and(String id) {
        response = request.when().put("/{id}", id);
    }

    @Then("I should receive updated task {} and {}")
    public void i_should_receive_updated_task_and(String expectedTask, String expectedStatus) {
        response.then().body("taskToBeDone", equalTo(expectedTask)).body("completed", equalTo(Boolean.parseBoolean(expectedStatus)));
    }


    //delete api testing
    @Given("I have a task to delete")
    public void i_have_a_task_to_delete(){
        request.when();
    }

    @When("I click delete api with {}")
    public void i_click_delete_api_with(String id) {
        response = request.when().delete("/{id}", id);
    }

    @Then("I should see and empty object")
    public void i_should_see_and_empty_object() {
        response.then().body(equalTo("{}"));
    }

}

