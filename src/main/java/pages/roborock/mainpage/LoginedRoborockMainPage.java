package pages.roborock.mainpage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.FindBy;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.plugin.SpecificDevicePage;
import pages.roborock.hint.AuthorityPages;
import pages.roborock.mainpage.device.SetDeviceWifiPage;
import utils.AppiumUtil;

import java.io.IOException;

public class LoginedRoborockMainPage extends Page {
    @AndroidFindBy(accessibility = "添加设备")
    private MobileElement plusImageView;

    @FindBy(xpath = "//android.widget.TextView[@text='按型号添加设备']")
    private MobileElement addDeviceTextView;

    @FindBy(xpath="//android.widget.Button[@text='进入设备']")
    private MobileElement specificDeviceButton;

    @FindBy(xpath="//android.widget.Button[@text='搜索设备']")
    private MobileElement searchForDeviceButton;
    public SetDeviceWifiPage clickAddDevice(String productName) {
        /*在已有设备时的页面
        spinAt(plusImageView);
        plusImageView.click();*/
        spinAt(searchForDeviceButton);
        searchForDeviceButton.click();
        new AuthorityPages().allowLocatingInUse();
        /*在已有设备时的页面
        spinAt(addDeviceTextView);
        addDeviceTextView.click();*/
        MobileElement productTextView = findElementByXPath("//android.widget.TextView[contains(@text,'" + productName + "')]",
                100);
        System.out.println(productTextView.getText());
        productTextView.click();
        return new SetDeviceWifiPage();
    }

    public SpecificDevicePage clickSpecificDevice() {
        //返回为插件界面，复用
        spinAt(specificDeviceButton);
        specificDeviceButton.click();
        return new SpecificDevicePage();

    }

    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/config.properties");
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver(Globals.params.get("platform"),
                Globals.params.get("MobileDevice"),
                true);

        new LoginedRoborockMainPage().clickAddDevice("G10S");
    }
}
