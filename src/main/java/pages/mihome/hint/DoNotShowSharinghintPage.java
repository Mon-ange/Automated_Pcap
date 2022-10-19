package pages.mihome.hint;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class DoNotShowSharinghintPage extends Page{
	@FindBy(xpath="//android.widget.Button[@text='不再提示']")
	private MobileElement doNotShowSharingButton;
	public void finishHint() {
		spinAt(doNotShowSharingButton);
		doNotShowSharingButton.click();
	}
}
