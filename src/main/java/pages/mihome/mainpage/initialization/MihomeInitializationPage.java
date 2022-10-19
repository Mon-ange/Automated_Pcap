package pages.mihome.mainpage.initialization;

import org.openqa.selenium.support.FindBy;

import pages.common.Page;
import pages.mihome.mainpage.NewMihomeMainPage;
import io.appium.java_client.MobileElement;

public class MihomeInitializationPage extends Page{
	//mihomePrivacyAgreement
	@FindBy(xpath = "//android.widget.TextView[@text='Agree']")
	public MobileElement agreeAndContinueTextView;
	
	//mihomeCustomerExperienceImprovementProgram
	@FindBy(xpath ="//android.widget.TextView[@text='同意']")
	public MobileElement agreeTextView;
	
	@FindBy(xpath="//android.widget.TextView[@text='立即体验']")
	public MobileElement experienceTextView;
	
	public NewMihomeMainPage initialize() {
		spinAt(agreeAndContinueTextView);
		agreeAndContinueTextView.click();
		spinAt(agreeTextView);
		agreeTextView.click();
		spinAt(experienceTextView);
		experienceTextView.click();

		return new NewMihomeMainPage();
		
		
	}
}
