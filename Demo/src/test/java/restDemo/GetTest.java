package restDemo; 
 
import java.util.ArrayList; 
import java.util.Random; 
 
import org.testng.annotations.Test; 
import org.testng.asserts.SoftAssert; 
 
import io.restassured.RestAssured; 
import io.restassured.http.Method; 
import io.restassured.path.json.JsonPath; 
import io.restassured.response.Response; 
import io.restassured.specification.RequestSpecification; 
 
public class GetTest { 
 
	static RequestSpecification httpRequest = RestAssured.with(); 
	public static int randomId; 
	static SoftAssert softAssertion = new SoftAssert(); 
 
	@Test 
	public static void getAndDeleteEmployee() { 
//		Specify the base URL to the RESTful web service 
		RestAssured.baseURI = "http://dummy.restapiexample.com/"; 
		httpRequest = RestAssured.with(); 
		getEmployees(); 
		getEmployee(); 
		deleteEmployee(); 
	} 
 
	public static void getEmployees() { 
		try { 
//		Wait before the next call because the page returns "too many requests" 
			Thread.sleep(15000); 
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
			System.out.println("Exception retrying"); 
			getEmployees(); 
		} 
	} 
 
	public static void getEmployee() { 
		try { 
//		Wait before the next call because the page returns "too many requests" 
			Thread.sleep(15000); 
			Response getEmployee = httpRequest.request(Method.GET, "/api/v1/employee/" + randomId); 
			JsonPath jsonPathEmployee = getEmployee.jsonPath(); 
			softAssertion.assertTrue(jsonPathEmployee.get("data.id").equals(randomId), "The employee Id is incorrect"); 
			System.out.println("Selected employee: " + jsonPathEmployee.get("data.employee_name").toString()); 
			System.out.println(getEmployee.asPrettyString()); 
 
		} catch (Exception e) { 
			System.out.println("Exception retrying"); 
			getEmployee(); 
		} 
	} 
 
	public static void deleteEmployee() { 
		try { 
//			Wait before the next call because the page returns "too many requests" 
			Thread.sleep(20000); 
			Response deleteEmployee = httpRequest.request(Method.DELETE, "/api/v1/delete/" + randomId); 
			JsonPath jsonPathdeleteEmployee = deleteEmployee.jsonPath(); 
			softAssertion.assertTrue(jsonPathdeleteEmployee.get("status").equals("success"), "The status is incorrect"); 
			System.out.println(deleteEmployee.asPrettyString()); 
 
		} catch (Exception e) { 
			System.out.println("Exception retrying"); 
			deleteEmployee(); 
		} 
 
	} 
}