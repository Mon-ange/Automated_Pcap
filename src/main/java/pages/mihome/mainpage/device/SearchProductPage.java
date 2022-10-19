package pages.mihome.mainpage.device;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.support.FindBy;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;

public class SearchProductPage extends Page{
	@FindBy(xpath="//android.widget.EditText[@text='请输入设备名称']")
	MobileElement productInputEditText;
	
	public ResetDevicePage searchProduct(String productText) {
		spinAt(productInputEditText);
		productInputEditText.clear();
		productInputEditText.sendKeys(productText);
		driver.hideKeyboard();
		/*List<MobileElement> elements = driver.findElementsByXPath("//android.widget.TextView");
		MobileElement toBeTestedProductRow = null;
		for(MobileElement element:elements) {
			System.out.println(element.getText());
			if(element.getText().equals(productText)) {
				toBeTestedProductRow = element;
				break;
			}		
		}
		if(toBeTestedProductRow!=null) {
			toBeTestedProductRow.click();
		}*/
		MobileElement element = findElementInWholePage("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView/android.widget.RelativeLayout[1]");
		element.click();
		return new ResetDevicePage();
	}
	
}
