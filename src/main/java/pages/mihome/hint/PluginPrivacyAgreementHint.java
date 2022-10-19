package pages.mihome.hint;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;


public class PluginPrivacyAgreementHint extends Page {
	public void agreePluginPrivacy() {
		MobileElement agreeAndContinueButton = findElementByXPath("//android.widget.Button[@text='同意并继续']", 30);
		if(agreeAndContinueButton.isDisplayed())
			agreeAndContinueButton.click();
	}
}
