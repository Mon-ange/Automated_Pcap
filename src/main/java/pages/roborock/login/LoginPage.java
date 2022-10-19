package pages.roborock.login;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.roborock.mainpage.NewRoborockMainPage;
import utils.AppiumUtil;

import java.io.IOException;

public class LoginPage extends Page {
    @FindBy(xpath="//android.widget.EditText[@text='请输入邮箱']")
    private MobileElement userNameEditText;

    @FindBy(xpath="//android.widget.EditText[@text='请输入密码']")
    private MobileElement passwordEditText;

    @FindBy(xpath = "//android.widget.Button[@text='登录']")
    private MobileElement loginButton;

    public void login(String userName,String password){
        userNameEditText.clear();
        userNameEditText.sendKeys(userName);
        passwordEditText.clear();
        passwordEditText.sendKeys(password);
        //wait for clickable loginButton
        sleep(1);
        loginButton.click();
        //wait for logging...
        sleep(6);

    }

    public static void main(String[] args) throws IOException {
        AppiumUtil.parseProperty("/config.properties");

        AppiumUtil.parseProperty("/appiumCapability.properties");

        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver("Android",
                Globals.params.get("MobileDevice"),
                true);
        new NewRoborockMainPage().clickLogin().login("capturepackage-cntest@roborock.com","Pcapcntest21");
    }
}
