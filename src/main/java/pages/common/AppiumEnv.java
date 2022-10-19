package pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppiumEnv {
    public AppiumDriverLocalService service;
    private AppiumDriver<MobileElement> driver;
    public AppiumEnv() throws IOException{
        this.service = AppiumDriverLocalService.buildDefaultService();
        this.service.start();
    }
    public AppiumDriver<MobileElement> setupDriver(String platformName, String model, Boolean noReset){
        String appPackage = Globals.params.get("appPackage");
        String appActivity = Globals.params.get("appActivity");
        if (platformName.equalsIgnoreCase(("android"))) {

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName","Android");
            desiredCapabilities.setCapability("deviceName",model);
            desiredCapabilities.setCapability("automationName","UiAutomator2");
            desiredCapabilities.setCapability("appPackage",appPackage);
            desiredCapabilities.setCapability("appActivity",appActivity);
            desiredCapabilities.setCapability("noReset",noReset);

            driver = new AndroidDriver<MobileElement>(service.getUrl(),desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.hideKeyboard();
            }
        System.out.println("pass");
        return driver;

    }
    public AppiumDriver<MobileElement> getDriver(){
        return this.driver;
    }
}
