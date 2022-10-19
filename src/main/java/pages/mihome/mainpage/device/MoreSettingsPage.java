package pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class MoreSettingsPage extends Page{
	@FindBy(xpath="//android.widget.Button[@text='下一步']")
	private MobileElement nextStepButton;
	
	public void setMore() {
		spinAt(nextStepButton);
		nextStepButton.click();
	}
}
