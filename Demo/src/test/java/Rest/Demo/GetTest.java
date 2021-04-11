package Rest.Demo;

import java.util.ArrayList;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetTest {

	@Test
	public void GetEmployees() {
//		Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://dummy.restapiexample.com/";

		RequestSpecification httpRequest = RestAssured.given();
//		Wait before the next call because the page returns "too many requests"
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		Getting all employees
		Response getEmployees = httpRequest.request(Method.GET, "/api/v1/employees");
		int statusCode = getEmployees.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Correct status code not returned");

//		Checking the status in the response body
		JsonPath jsonPathEmployees = getEmployees.jsonPath();
		Assert.assertEquals(jsonPathEmployees.get("status"), "success", "The status is incorrect");
		System.out.println("Status in the response body is: " + jsonPathEmployees.get("status").toString());

//		Wait before the next call because the page returns "too many requests"
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		Requesting random employee
		ArrayList<Integer> id = jsonPathEmployees.get("data.id");
		Random rand = new Random();
		int randomId = rand.nextInt(id.size() + 1);
		Response getEmployee = httpRequest.request(Method.GET, "/api/v1/employee/" + randomId);
		JsonPath jsonPathEmployee = getEmployee.jsonPath();
		Assert.assertTrue(jsonPathEmployee.get("data.id").equals(randomId), "The employee Id is incorrect");
		System.out.println("Selected employee: " + jsonPathEmployee.get("data.employee_name").toString());
		System.out.println(getEmployee.asPrettyString());

//		Wait before the next call because the page returns "too many requests"
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		Delete the selected employee
		System.out.println("Delete employee: " + jsonPathEmployee.get("data.employee_name").toString());
		Response deleteEmployee = httpRequest.request(Method.DELETE, "/api/v1/delete/" + randomId);
		JsonPath jsonPathdeleteEmployee = deleteEmployee.jsonPath();
		Assert.assertEquals(jsonPathdeleteEmployee.get("message"), "Successfully! Record has been deleted",
				"The Employee was not deleted!");
		System.out.println(deleteEmployee.asPrettyString());
	}

}
