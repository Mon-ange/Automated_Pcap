package pages.mihome.mainpage.device;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import io.appium.java_client.MobileElement;

public class AddDevicePage extends Page{
	
	@FindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView[2]")
	MobileElement searchProductImageView;
	public SearchProductPage clickSearch() {
		spinAt(searchProductImageView);
		searchProductImageView.click();
		return new SearchProductPage();
	}
	
}
