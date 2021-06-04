package restDemo; 
 
import static Rest.Demo.App.getApp;

import org.testng.annotations.Test; 
import Rest.Demo.App; 
 
public class GetTest { 
 
	 
	App app = getApp();
	
	@Test 
	public void getAndDeleteEmployee() { 

		app.setUp().getEmployees().getEmployee().deleteEmployee();

	} 
 

}