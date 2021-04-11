package seleniumVisitAmazon;

public class Locators {
	public static final String searchTextBox="//input[@id='twotabsearchtextbox']";
	public static final String searchSubmitButton="//input[@id='nav-search-submit-button']";
	public static final String firstItem="//div[@data-index='1']//img";
	public static final String jsItemPriceOnSearch="return document.getElementsByClassName('a-size-base a-link-normal a-text-normal')[0].getElementsByClassName('a-offscreen').valueOf('outerText')[0].innerText;";
	public static final String itemPriceInItem="//span[@id='newBuyBoxPrice']";
	public static final String addToCartButton="//input[@id='add-to-cart-button']";
	public static final String amountInCart="(//b[contains(text(),'Cart subtotal')])[1]/parent::span/following-sibling::span";
	public static final String proceedToCheckoutButton="//a[@id='hlb-ptc-btn-native']";
	
	
	public static final String emailField="//input[@id='ap_email']";
	public static final String continueButton="//input[@id='continue']";
	public static final String passwordField="//input[@id='ap_password']";
	public static final String signInSubmitButton="//input[@id='signInSubmit']";
}
