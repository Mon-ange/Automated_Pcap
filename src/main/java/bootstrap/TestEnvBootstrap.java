package bootstrap;

import org.testng.ITestContext;
import org.testng.TestListenerAdapter;
import pages.common.AppiumEnv;
import pages.common.Globals;
import utils.AppiumUtil;

import java.io.IOException;

public class TestEnvBootstrap extends TestListenerAdapter {
    public void onStart(ITestContext testContext) {
        //Invoked after the test class is instantiated and before any configuration method is called.
        try {
            bootstrap();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void bootstrap() throws IOException {
        AppiumUtil.parseProperty("/config.properties");
        Globals.env = new AppiumEnv();
        Globals.driver = Globals.env.setupDriver(Globals.params.get("platformName"),
                Globals.params.get("deviceName"),
                Boolean.parseBoolean(Globals.params.get("noReset")));


    }
}

