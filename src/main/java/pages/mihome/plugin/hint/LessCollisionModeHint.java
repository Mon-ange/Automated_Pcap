package pages.mihome.plugin.hint;

import pages.common.Page;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LessCollisionModeHint extends Page implements StartUpHint {
	@AndroidFindBy(accessibility = "collision_guide_ok")
	MobileElement nextStepButton;
	
	@Override
	public void acceptHint() {
		spinAt(nextStepButton);
		nextStepButton.click();
	}
	
	
}
