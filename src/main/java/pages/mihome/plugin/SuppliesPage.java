package pages.mihome.plugin;

import java.util.List;
import java.io.IOException;

import utils.AppiumUtil;
import pages.common.Page;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import pages.mihome.plugin.supplies.*;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SuppliesPage extends Page{
	public void checkAll() {
		List<MobileElement> elements = getSuppliesButtons();
		for(MobileElement element:elements) {
			element.click();
			goBack();
			sleep(1);//wait for element loading
		}
	}
	
	private List<MobileElement> getSuppliesButtons(){
		return driver.findElementsByXPath("//android.view.ViewGroup[contains(@content-desc,'suppliesPageList_')]");
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		//unit test
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android", "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().checkSupplies().checkAll();
	}
}
