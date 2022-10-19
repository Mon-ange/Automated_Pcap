package pages.mihome.plugin;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.io.IOException;
import java.time.Duration;

public class RemotePage extends Page {
    @AndroidFindBy(accessibility = "remote_top_key")
    @iOSXCUITFindBy(accessibility = "remote_top_key")
    private MobileElement topKey;

    @AndroidFindBy(accessibility = "remote_btn_3")
    @iOSXCUITFindBy(accessibility = "remote_btn_3")
    private MobileElement dockBackButton;
   public SpecificDevicePage remote(){
       spinAt(topKey);
       new TouchAction(driver)
               .press(PointOption.point(548,870))
               .release()
               .perform();
       sleep(3);
       dockBackButton.click();
       sleep(30);//wait for logs
       return new SpecificDevicePage();
   }
    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver("Android", "Redmi 5", true);
        new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickRemote().remote();

    }
}
