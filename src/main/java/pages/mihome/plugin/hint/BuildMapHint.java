package pages.mihome.plugin.hint;

import pages.common.Page;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class BuildMapHint extends Page implements StartUpHint{
	@AndroidFindBy(accessibility = "build_map_guide_ok")
	private MobileElement okButton;
	
	@Override
	public void acceptHint() {
		spinAt(okButton);
		okButton.click();
		
	}
	
	
}
