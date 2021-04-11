package seleniumVisitAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class VisitAmazonPage {

	public static WebDriver driver = null;

	public void visitAmazon() throws InterruptedException {

		String amount;

		System.setProperty("webdriver.chrome.driver", "../Demo/src/test/java/seleniumVisitAmazon/BrowserDriver/chromedriver.exe");
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("webdriver.chrome.driver"));
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1280,1020");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 5, 100);

//		1. Visit amazon.com Page
		driver.get("https://amazon.com");
		Thread.sleep(3000);

//		2. Search for Book 'qa testing for beginners'
		WebElement searchBox = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.searchTextBox)));
		searchBox.sendKeys("qa testing for beginners");

//		Click on search button
		WebElement searchButton = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.searchSubmitButton)));
		searchButton.click();
		Thread.sleep(2000);

//		Capturing initial amount in search results
		amount = (String) ((JavascriptExecutor) driver).executeScript(Locators.jsItemPriceOnSearch);
		System.out.println("Initial item amount = " + amount);

//		3. Click on 1st item in the listed results.
		WebElement firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.firstItem)));
		firstItem.click();

//		Capturing amount in item
		String amountInItem = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.itemPriceInItem))).getText();
		System.out.println("In item amount = " + amountInItem);

//		4. Before Click on Add to Cart assert price from Step3. 
		Assert.assertEquals(amount, amountInItem,"Amount in item was not the same as ");

//		5. Click on Add to Cart.
		WebElement addToCart = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.addToCartButton)));
		addToCart.click();

//		Capturing amount before checkout
		String amountInCart = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.amountInCart)))
				.getText();
		System.out.println("In cart amount = " + amountInCart);

//		6. Before Click on Proceed to Checkout asset price from Step3.
		Assert.assertEquals(amount, amountInCart,"Amount in cart was not the same as the initial one");

//		7. Click on proceed to checkout
		WebElement proceedToCheckout = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.proceedToCheckoutButton)));
		proceedToCheckout.click();

//		8. On the checkout page assert price from Step3.
		// !!!not able to get to the checkout page because i don't have amazon account
		WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.emailField)));
		emailField.sendKeys("don't have amazon account!!!");
		Thread.sleep(2000);
		emailField.clear();
		emailField.sendKeys("Execution is about to end!!!");
		System.out.println("Execution is about to end!!!");
		Thread.sleep(3000);
		driver.quit();
		System.out.println("Execution complete");

	}
}
