package pages.mihome.hint;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class AuthorityPages extends Page{
	@FindBy(xpath="//android.widget.Button[@text='仅在使用中允许']")
	private MobileElement allowLocatingInUseButton;
	@FindBy(xpath="//android.widget.Button[@text='允许一次']")
	private MobileElement allowWifiButton;
	
	public void allowLocatingInUse() {
		spinAt(allowLocatingInUseButton);
		allowLocatingInUseButton.click();
	}
	
	public void allowWifi() {
		while (true) {
			if (allowWifiButton.getText().equals("允许一次")) {
				allowWifiButton.click();
				break;
			}
		}
	}
}
