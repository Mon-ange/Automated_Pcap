package pages.mihome.plugin;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.IOException;

public class TimerPage extends Page {
    @AndroidFindBy(accessibility = "timer_list_guide_ok")
    MobileElement okButton;

    @AndroidFindBy(accessibility = "timer_setting_add")
    MobileElement timerSettingViewGroup;

    @AndroidFindBy(accessibility = "TimerSettingList_0s1")
    @iOSXCUITFindBy(accessibility = "TimerSettingList_0s1")
    private MobileElement theFirstTimer;
    public EditTimerPage startTimer(){
        /*如果进行单元调试注释okButton*/
        spinAt(okButton);
        okButton.click();
        spinAt(timerSettingViewGroup);
        timerSettingViewGroup.click();
        return new EditTimerPage();
    }
    public SpecificDevicePage backToSpec(){
        spinAt(theFirstTimer);
        goBack();
        goBack();
        return new SpecificDevicePage();
    }
    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver("Android", "xiaomi",
                true);
        new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickTimer().goBack();
    }
}
