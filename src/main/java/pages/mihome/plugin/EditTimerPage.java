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
import java.util.Calendar;
import java.util.List;

public class EditTimerPage extends Page {

    @AndroidFindBy(accessibility = "timer_sel_alert_btn2")
    @iOSXCUITFindBy(accessibility = "timer_sel_alert_btn2")
    private MobileElement confirmAlert;

    @AndroidFindBy(accessibility = "timer_setting_start_time")
    @iOSXCUITFindBy(accessibility = "timer_setting_start_time")
    private MobileElement startTime;

    @AndroidFindBy(accessibility = "timer_confirm_button")
    @iOSXCUITFindBy(accessibility = "timer_confirm_button")
    private MobileElement confirmTimer;

    @AndroidFindBy(accessibility = "timer_picker_wrapper")
    @iOSXCUITFindBy(accessibility = "timer_picker_wrapper")
    private MobileElement timerPickerWrapper;



    public Calendar setTime(){
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE,4);
        return time;
    }

    public TimerPage confirmTimer() {
        int hours= setTime().get(Calendar.HOUR_OF_DAY);
        int minutes = setTime().get(Calendar.MINUTE);
        spinAt(startTime);
        startTime.click();
        spinAt(confirmAlert);
        List<MobileElement> times = driver.findElementsByClassName("android.widget.TextView");
        /*for (int i = 0; i < times.size(); i++) {
            System.out.println(i + " text: " + times.get(i).getText());
        }*/
        MobileElement minuteElement = times.get(10);
        while (Integer.parseInt(minuteElement.getText()) != minutes) {
            new TouchAction(driver)
                    .press(PointOption.point(578, 1962))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(578, 1705))
                    .release()
                    .perform();
            times = driver.findElementsByClassName("android.widget.TextView");
            minuteElement = times.get(10);
        }
        confirmAlert.click();
        spinAt(startTime);
        confirmTimer.click();
        return new TimerPage();
    }
    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver("Android", "Redmi 5", true);
        new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickTimer().startTimer().confirmTimer().backToSpec().dockBack();

    }
}
