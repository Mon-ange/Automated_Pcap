package pages.mihome.hint;

import pages.common.Page;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

public class CancelledUpdatePages extends Page {
    @FindBy(xpath = "//android.widget.Button[@text='取消']")
    private MobileElement cancelledButton;

    public void cencelledUpdateApp(){
        spinAt(cancelledButton);
        cancelledButton.click();
    }
}
