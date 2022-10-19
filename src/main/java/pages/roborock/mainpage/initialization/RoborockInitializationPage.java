package pages.roborock.mainpage.initialization;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.Page;
import pages.roborock.mainpage.NewRoborockMainPage;

public class RoborockInitializationPage extends Page {
    @FindBy(xpath = "//android.widget.TextView[@text='同意']")
    MobileElement agreeAndContinueTextView;

    public NewRoborockMainPage initialize() {
        spinAt(agreeAndContinueTextView);
        agreeAndContinueTextView.click();
        return new NewRoborockMainPage();
    }
}
