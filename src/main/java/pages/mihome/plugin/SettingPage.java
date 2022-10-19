package pages.mihome.plugin;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import org.openqa.selenium.support.FindBy;


import pages.common.Page;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.IOException;

public class SettingPage extends Page{
	 @AndroidFindBy(accessibility = "main_title")
	 private MobileElement settingTextView;
	 
	 @AndroidFindBy(accessibility = "setting_page_soundpackage_volume")
	 private MobileElement soundPackageViewGroup;
		
	 @AndroidFindBy(accessibility = "setting_page_supplies")
	 private MobileElement suppliesViewGroup;

	 @AndroidFindBy(accessibility = "setting_page_timer")
	 private MobileElement timerViewGroup;


	@AndroidFindBy(accessibility = "setting_page_remote")
	private MobileElement remoteViewGroup;

	 public LanguageAndVolumePage clickSoundPackage() {
		 spinAt(soundPackageViewGroup);
		 soundPackageViewGroup.click();		 
		 return new LanguageAndVolumePage();
		 
	 }
	 public ProductGuidePage clickproductGuide() {
		 spinAt(settingTextView);
		 findElementInWholePage("//android.widget.TextView[@text='产品百科']").click();
		 return new ProductGuidePage();
	 }
	 
	 public FirmwareUpdatePage clickFirmwareUpdate() {
		 //防止在设置界面出来前scroll
		 spinAt(settingTextView);
		 findElementInWholePage("//android.widget.TextView[contains(@text,'固件')]").click();
		 return new FirmwareUpdatePage();
	 }

	 public SuppliesPage checkSupplies() {
		 spinAt(settingTextView);
		 findElementInWholePage("//android.view.ViewGroup[@content-desc='setting_page_supplies']").click();
		 return new SuppliesPage();
	 }
	 public TimerPage clickTimer(){
		 spinAt(timerViewGroup);
		 timerViewGroup.click();
		 return new TimerPage();
	 }

	 public RemotePage clickRemote(){
		 spinAt(settingTextView);
		 findElementInWholePage("//android.widget.TextView[@text='遥控模式']").click();
		 return new RemotePage();
	 }
	public static void main(String[] args) throws IOException {
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android",  "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickFirmwareUpdate().immediatelyUpdate().goBack();
	}

}

