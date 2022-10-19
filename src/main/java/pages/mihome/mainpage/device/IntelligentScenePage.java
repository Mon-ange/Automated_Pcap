package pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class IntelligentScenePage extends Page{
	@FindBy(xpath="//android.widget.Button[@text='下一步']")
	private MobileElement nextStepButton;
	
	public void setIntelligentScene() {
		spinAt(nextStepButton);
		nextStepButton.click();
	}
}
