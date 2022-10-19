package apptest.pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import apptest.pages.common.Page;
import io.appium.java_client.MobileElement;

public class SetDeviceNamePage extends Page{
	@FindBy(xpath="//android.widget.Button[@text='下一步']")
	private MobileElement nextStepButton;
	
	public void setDeviceName() {
		spinAt(nextStepButton);
		nextStepButton.click();
	}
	
}
