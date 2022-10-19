package pages.mihome.login;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.NewMihomeMainPage;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

public class LoginPage extends Page{
	@FindBy(xpath = "//android.widget.EditText[@text='邮箱/手机号码/小米ID']")
	MobileElement userNameEditText;
	@FindBy(xpath = "//android.widget.EditText[@text='密码']")
	MobileElement passwordEditText;
	
	@FindBy(xpath = "//android.widget.Button[@text='登录']")
	MobileElement loginButton;
	
	@FindBy(className = "android.widget.CheckBox")
	private MobileElement agreeWithLicenseCheckBox;

	@FindBy(xpath="//android.widget.TextView[@text='中国大陆']")
	private MobileElement switchLocTextView;

	@FindBy(xpath = "//android.widget.EditText[@text='搜索']")
	MobileElement locationEditText;

	@FindBy(xpath = "//android.widget.Button[@text='确认切换']")
	MobileElement switchConfirmButton;
	public void login(String userName, String password,String productLocation) {

		if(!productLocation.equals("中国大陆")){
			spinAt(switchLocTextView);
			switchLocTextView.click();
			spinAt(locationEditText);
			locationEditText.click();
			locationEditText.clear();
			locationEditText.sendKeys(productLocation);
			driver.hideKeyboard();
			MobileElement locationTextView = findElementByXPath("//android.widget.TextView[@text='"+productLocation+"']",10);
			locationTextView.click();

			switchConfirmButton.click();
			new NewMihomeMainPage().clickLogin();
		}
		//MobileElement userNameEditText = findElementByXPath("//android.widget.EditText[@text='邮箱/手机号码/小米ID']",2);
		userNameEditText.clear();
		userNameEditText.sendKeys(userName);
		passwordEditText.clear();
		passwordEditText.sendKeys(password);
		agreeWithLicenseCheckBox.click();

		loginButton.click();
		
	}

	public static void main(String[] args) throws IOException {
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android",  "Redmi 5", true);
		System.out.println(Globals.params.get("user.username"));
		new NewMihomeMainPage().clickLogin()
				.login(Globals.params.get("user.username"), Globals.params.get("user.password"),"中国大陆");

	}
}
