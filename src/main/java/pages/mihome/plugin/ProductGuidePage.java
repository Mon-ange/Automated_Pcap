package pages.mihome.plugin;

import java.io.IOException;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Sleeper;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductGuidePage extends Page{
	@FindBy(xpath="//android.widget.ImageView[@content-desc=\"返回\"]")
	private MobileElement goBackImageView;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//unit test
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android",  "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickproductGuide().goBack();
	}

}
