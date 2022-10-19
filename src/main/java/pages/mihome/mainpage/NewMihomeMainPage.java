package pages.mihome.mainpage;
import pages.mihome.login.LoginPage;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class NewMihomeMainPage extends Page{
	
	@FindBy(xpath = "//android.widget.TextView[@text='立即登录']")
	MobileElement loginImmediately;
	
	
	public LoginPage clickLogin() {
		spinAt(loginImmediately);
		loginImmediately.click();
		return new LoginPage();
	}

}
