package pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.HashMap;
import java.util.Map;

public class Globals {
    public static AppiumEnv env;
    public static AppiumDriver<MobileElement> driver;
    public static Map<String, String> params = new HashMap<String, String>();

}
