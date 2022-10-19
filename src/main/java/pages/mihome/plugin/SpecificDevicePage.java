package pages.mihome.plugin;

import java.io.IOException;
import java.util.Scanner;

import org.openqa.selenium.support.FindBy;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SpecificDevicePage extends Page{
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc='home_setting_button' and @clickable='true']")
	@iOSXCUITFindBy(accessibility="home_setting_button")
	private MobileElement settingViewGroup;
	
	
	@AndroidFindBy(accessibility="home_clean_button")
	@iOSXCUITFindBy(accessibility="home_clean_button")
	private MobileElement cleanViewGroup;
	

	@AndroidFindBy(accessibility="sub_title")
	@iOSXCUITFindBy(accessibility="sub_title")
	private MobileElement statusHint;
	
	@AndroidFindBy(accessibility="home_dock_button")
	@iOSXCUITFindBy(accessibility="home_dock_button")
	private MobileElement dockViewGroup;
	
	@AndroidFindBy(accessibility="action_item_0")
	@iOSXCUITFindBy(accessibility="action_item_0")
	private MobileElement dockHintViewGroup;
	


	
	public SettingPage clickSetting()  {
			while(true) {
				if(statusHint.getText().contains("充电") ){
					settingViewGroup.click();
					try{
						findElementByXPath("//android.widget.TextView[@text='取消']",
								5).click();
					}catch(NullPointerException e){
					}
					break;
				}
			}

			return new SettingPage();
	}
	
	public void doClean() {
		spinAt(cleanViewGroup);
		cleanViewGroup.click();
		sleep(30);//wait for varies status such as 'mopwashing' at the same time clean for 30s
		while(true) {
			if(statusHint.getText().equals("清洁中")) {
				dockViewGroup.click();
				spinAt(dockHintViewGroup);
				dockHintViewGroup.click();
				break;
			}
		}
		sleep(60);//normal dock and upload logs for 1min
	}
	public void dockBack(){
		sleep(120);//wait for the timer
		while(true) {
			if(statusHint.getText().equals("清洁中")) {
				dockViewGroup.click();
				spinAt(dockHintViewGroup);
				dockHintViewGroup.click();
				break;
			}
		}
		sleep(40);//wait for logs
	}
	public void makeError() {
		System.out.println("please make some errors manually and enter \'y\'");
		Scanner scanner = new Scanner(System.in);
		String result = scanner.next();
		scanner.close();
		if(result.equals("y")) {
			MobileElement errorHint = driver.findElementByXPath("//android.view.ViewGroup[contains(@content-desc,'error_key_')]");
			errorHint.click();
			sleep(3);//visit the error image and wait for upload
		}
		else {
			System.out.println("Please input correctly!");
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		//unit test
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android", "Redmi 5", true);
		//unit test for clean
		//new LoginedMihomeMainPage().clickSpecificDevice().doClean();
		
		//unit test for errorimage
		//new LoginedMihomeMainPage().clickSpecificDevice().makeError();
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting();
	}
}
