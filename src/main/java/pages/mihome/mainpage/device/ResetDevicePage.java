package pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class ResetDevicePage extends Page{
	@FindBy(xpath = "//android.widget.Button[@text='设备已重置']")
    private MobileElement deviceResetedButton;
	public SetDeviceWifiPage clickReseted() {
		spinAt(deviceResetedButton);
		deviceResetedButton.click();
		return new SetDeviceWifiPage();
		
	}
}
