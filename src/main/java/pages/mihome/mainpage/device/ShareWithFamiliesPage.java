package apptest.pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import apptest.pages.common.Page;
import apptest.pages.mihome.hint.DoNotShowSharinghintPage;
import io.appium.java_client.MobileElement;

public class ShareWithFamiliesPage extends Page{
	@FindBy(xpath="//android.widget.Button[@text='暂不分享']")
	private MobileElement doNotShareButton;

	public void doNotShare() {
		spinAt(doNotShareButton);
		doNotShareButton.click();
		new DoNotShowSharinghintPage().finishHint();
	}
	
	
	
}
