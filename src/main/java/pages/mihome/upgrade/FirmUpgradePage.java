package apptest.pages.mihome.upgrade;

import apptest.pages.common.Page;
import io.appium.java_client.MobileElement;

public class FirmUpgradePage extends Page {


    public String getCurrentVersion() {
        MobileElement currentVersion = driver.findElementByXPath("//*[contains(@text,'当前版本')]");
        return currentVersion.getText().split("当前版本：")[1];
    }

}
