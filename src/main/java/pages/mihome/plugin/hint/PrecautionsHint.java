package pages.mihome.plugin.hint;

import pages.common.Page;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PrecautionsHint extends Page implements StartUpHint{
	


	@Override
	public void acceptHint() {
		MobileElement nextStepButton = findElementByAccessibilityId("prohibited_guide_ok",240);
		nextStepButton.click();
	}
}
