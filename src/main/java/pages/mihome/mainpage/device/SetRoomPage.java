package apptest.pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import apptest.pages.common.Page;
import io.appium.java_client.MobileElement;

public class SetRoomPage extends Page{
	@FindBy(xpath = "//android.widget.TextView[@text='卧室']")
    private MobileElement bedRoomTextView;
	
	@FindBy(xpath = "//android.widget.Button[@text='下一步']")
    private MobileElement nextStepButton;
	
	public void setRoom() {
		findElementByXPath("//android.widget.TextView[@text='卧室']",
				240);
		bedRoomTextView.click();
		spinAt(nextStepButton);
		nextStepButton.click();
		
	}

}
