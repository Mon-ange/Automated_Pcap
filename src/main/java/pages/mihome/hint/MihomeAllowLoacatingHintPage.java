package pages.mihome.hint;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class MihomeAllowLoacatingHintPage extends Page{
	 @FindBy(xpath = "//android.widget.Button[@text='下一步']")
	    private MobileElement nextStepButton;
	 
	 public void finishHint() {
		 spinAt(nextStepButton);
		 nextStepButton.click();
	 }
}
