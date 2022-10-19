package pages.roborock.hint;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.Page;

public class PrivacyAgreementHintPage extends Page {

    @FindBy(xpath="//android.widget.TextView[@text='同意']")
    private MobileElement agreeButton;
    public void agreePrivacy(){
        spinAt(agreeButton);
        agreeButton.click();
    }
}
