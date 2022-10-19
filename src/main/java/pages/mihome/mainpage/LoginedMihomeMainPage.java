package pages.mihome.mainpage;
import pages.mihome.mainpage.device.AddDevicePage;
import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import pages.common.Page;

import pages.mihome.plugin.SpecificDevicePage;
;import java.io.IOException;


public class LoginedMihomeMainPage extends Page{
	@FindBy(xpath="//android.widget.ImageView[@content-desc=\"添加设备\"]")
	private MobileElement plusImageView;
	
	@FindBy(xpath="//android.widget.TextView[@text='添加设备']")
	private MobileElement addDeviceTextView;
	
	public AddDevicePage clickAddDevice() {
		spinAt(plusImageView);
		plusImageView.click();
		spinAt(addDeviceTextView);
		addDeviceTextView.click();
		return new AddDevicePage();
	}
	
	public SpecificDevicePage clickSpecificDevice(String productName) {
		MobileElement productTextView =findElementByXPath("//android.widget.TextView[@text='"+productName+"']",
				20);
		System.out.println(productTextView.getText());
		productTextView.click();
		return new SpecificDevicePage();
		
	}
	public static void main(String[] args) throws IOException {
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android", "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV");
	}
}
