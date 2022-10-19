package pages.mihome.plugin;

import java.io.IOException;

import org.openqa.selenium.support.FindBy;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;

public class FirmwareUpdatePage extends Page{
	@FindBy(xpath="//android.widget.TextView[@text='立即更新']")
	private MobileElement immediatelyUpdateTextView;
	
	public FirmwareUpdatePage immediatelyUpdate() {
		MobileElement updatedVersionView = findElementByXPath("//android.view.View[contains(@text,'已是最新版本')]", 5);
		if(updatedVersionView!=null) 	return new FirmwareUpdatePage();
		spinAt(immediatelyUpdateTextView);
		immediatelyUpdateTextView.click();
		while(true){
			updatedVersionView = findElementByXPath("//android.view.View[contains(@text,'已是最新版本')]", 5);
			if(updatedVersionView!=null)
				return new FirmwareUpdatePage();
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		//unit test
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android",  "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickFirmwareUpdate().immediatelyUpdate().goBack();
	}

	
	
}
