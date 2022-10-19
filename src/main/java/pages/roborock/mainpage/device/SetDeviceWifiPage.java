package pages.roborock.mainpage.device;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.plugin.hint.StartUpHints;
import pages.roborock.hint.PrivacyAgreementHintPage;
import pages.roborock.mainpage.LoginedRoborockMainPage;
import utils.AppiumUtil;

import java.io.IOException;

public class SetDeviceWifiPage extends Page {
    @FindBy(xpath = "//android.widget.Button[@text='下一步']")
    private MobileElement nextStepButton;
    @FindBy(xpath = "//android.widget.EditText")
    private MobileElement wifiPwdTextView;


    public void connectWifi(String wifiPwdText) {
        //石头会自动使用手机连接的wifi，不需要选择ssid
        spinAt(wifiPwdTextView);
        wifiPwdTextView.clear();
        wifiPwdTextView.sendKeys(wifiPwdText);
        driver.hideKeyboard();

        spinAt(nextStepButton);
        nextStepButton.click();

        MobileElement immediatelyStart = findElementByXPath("//android.widget.Button[@text='立即使用']",
                300);
        immediatelyStart.click();

    }

    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/config.properties");
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver(Globals.params.get("platform"),
                Globals.params.get("MobileDevice"),
                true);
        new LoginedRoborockMainPage()
                .clickAddDevice("G10S")
                .connectWifi(Globals.params.get("wifiPwd"));
        new PrivacyAgreementHintPage().agreePrivacy();
        new StartUpHints().acceptAllHints();
    }
}
