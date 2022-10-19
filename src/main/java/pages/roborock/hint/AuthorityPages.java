package pages.roborock.hint;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.Page;

public class AuthorityPages extends Page {
    @FindBy(xpath="//android.widget.Button[@text='仅在使用中允许']")
    private MobileElement allowLocatingInUseButton;

    @FindBy(xpath="//android.widget.Button[@text='仅在使用中允许']")
    private MobileElement allowTakingPhotoButton;

    public void allowLocatingInUse() {
        spinAt(allowLocatingInUseButton);
        allowLocatingInUseButton.click();
        /*已添加时
        spinAt(allowTakingPhotoButton);
        allowTakingPhotoButton.click();*/
    }
}
