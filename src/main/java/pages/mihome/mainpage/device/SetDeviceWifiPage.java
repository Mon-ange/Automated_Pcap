package pages.mihome.mainpage.device;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.common.Page;
import pages.mihome.hint.AuthorityPages;
import io.appium.java_client.MobileElement;

public class SetDeviceWifiPage extends Page{
	@FindBy(xpath="//android.widget.EditText[@password='true']")
	private MobileElement wifiPasswordEditText;
	
	@FindBy(xpath="//android.widget.TextView[@text='下一步']")
	private MobileElement nextStepTextView;
	
	private void selectWifi(String ssidText) {
		HashMap<String,String> clickableFilter = new HashMap<String,String>();
    	clickableFilter.put("clickable", "true");
    	MobileElement selectWifiTextView = findElementByXPath("//android.widget.TextView[@text='选择Wi-Fi']", 
    														240,
    														clickableFilter);
		selectWifiTextView.click();
		List<MobileElement> wifiNames = driver.findElementsByXPath("//android.widget.TextView[@text='" + ssidText + "']");
		wifiNames.get(0).click();
	}
	private void setWifiPassword(String wifiPwdText) {
		spinAt(wifiPasswordEditText);
		wifiPasswordEditText.clear();
		wifiPasswordEditText.sendKeys(wifiPwdText);
		driver.hideKeyboard();
	}
	public void connectWifi(String ssidText,String wifiPwdText) {
		selectWifi(ssidText);
		setWifiPassword(wifiPwdText);
		spinAt(nextStepTextView);
		nextStepTextView.click();
		
		new AuthorityPages().allowWifi();
		new AuthorityPages().allowWifi();
		
	}

}
