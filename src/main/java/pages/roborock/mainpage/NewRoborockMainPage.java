package pages.roborock.mainpage;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.roborock.login.LoginPage;
import pages.roborock.mainpage.initialization.RoborockInitializationPage;
import utils.AppiumUtil;

import java.io.IOException;

public class NewRoborockMainPage extends Page {
    @FindBy(xpath="//android.widget.Button[@text='登录']")
    private MobileElement loginButton;
    @FindBy(id="com.roborock.smart:id/login_other")
    private MobileElement loginBymailImageView;
    public LoginPage clickLogin(){
        spinAt(loginButton);
        loginButton.click();
        spinAt(loginBymailImageView);
        loginBymailImageView.click();
        return new LoginPage();
    }

    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/config.properties");
        AppiumUtil.parseProperty("/appiumCapability.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver(Globals.params.get("platform"),
                Globals.params.get("MobileDevice"),
                true);
        Globals.driver.resetApp();
        new RoborockInitializationPage().initialize()
                .clickLogin().login("capturepackage-cntest@roborock.com","Pcapcntest21");
    }
}
