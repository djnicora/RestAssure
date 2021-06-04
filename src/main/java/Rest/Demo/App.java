package Rest.Demo;

import java.util.ArrayList;
import java.util.Random;

import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class App {

	static RequestSpecification httpRequest = RestAssured.with();
	public static int randomId;
	static SoftAssert softAssertion = new SoftAssert();

	private App() {
		// hide it
	}

	public static App getApp() {
		return new App();
	}

	public App setUp() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		httpRequest = RestAssured.with();
		return this;
	}

	public App getEmployees() {
		try {
//		Wait before the next call because the page returns "too many requests" 
			Thread.sleep(4000);
//		Getting all employees 
			Response getEmployees = httpRequest.request(Method.GET, "/api/v1/employees");
			int statusCode = getEmployees.getStatusCode();
			softAssertion.assertEquals(statusCode, 200, "Correct status code not returned");

//		Checking the status in the response body 
			JsonPath jsonPathEmployees = getEmployees.jsonPath();
			softAssertion.assertEquals(jsonPathEmployees.get("status"), "success", "The status is incorrect");
			System.out.println("Status in the response body is: " + jsonPathEmployees.get("status").toString());
//		Requesting random employee 
			ArrayList<Integer> id = jsonPathEmployees.get("data.id");
			Random rand = new Random();
			randomId = rand.nextInt(id.size() + 1);

		} catch (Exception e) {
			System.out.println("Exception retrying(too many requests)");
			getEmployees();
		}
		return this;
	}

	public App getEmployee() {
		try {
//		Wait before the next call because the page returns "too many requests" 
			Thread.sleep(4000);
			Response getEmployee = httpRequest.request(Method.GET, "/api/v1/employee/" + randomId);
			JsonPath jsonPathEmployee = getEmployee.jsonPath();
			softAssertion.assertTrue(jsonPathEmployee.get("data.id").equals(randomId), "The employee Id is incorrect");
			System.out.println("Selected Random employee: " + jsonPathEmployee.get("data.employee_name").toString());
			System.out.println(getEmployee.asPrettyString());

		} catch (Exception e) {
			System.out.println("Exception retrying(too many requests)");
			getEmployee();
		}
		return this;
	}

	public void deleteEmployee() {
		try {
//			Wait before the next call because the page returns "too many requests" 
			Thread.sleep(4000);
			Response deleteEmployee = httpRequest.request(Method.DELETE, "/api/v1/delete/" + randomId);
			JsonPath jsonPathdeleteEmployee = deleteEmployee.jsonPath();
			softAssertion.assertTrue(jsonPathdeleteEmployee.get("status").equals("success"), "The status is incorrect");
			System.out.println(deleteEmployee.asPrettyString());

		} catch (Exception e) {
			System.out.println("Exception retrying(too many requests)");
			deleteEmployee();
		}
	}
}
