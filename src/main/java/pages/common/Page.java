package pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.PointOption.point;

public class Page {
    public enum DIRECTION {
        UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT;
    }
    public static AppiumDriver<MobileElement> driver;
    public Page() {
        this.driver = Globals.driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(60)), this);
    }

    public void goBack() {
        ((AndroidDriver)driver).pressKey(new KeyEvent(AndroidKey.BACK));
    }
    public void click(int x, int y) {
        (new TouchAction(driver)).tap(PointOption.point(x, y)).perform();
    }
    public void clickBlankArea() {
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width * 0.5);
        int y = (int) (size.height * 0.2);
        click(x, y);
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void swipeTo(DIRECTION direction) {
        Dimension size = driver.manage().window().getSize();

        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;

        switch (direction) {
            case LEFT:
                startX = (int) (size.width * 0.90);
                startY = (int) (size.height / 2);
                endX = (int) (size.width * 0.1);
                endY = startY;
                break;

            case RIGHT:
                startX = (int) (size.width * 0.1);
                startY = (int) (size.height / 2);
                endX = (int) (size.width * 0.90);
                endY = startY;
                break;

            case DOWN:
                startX = (int) (size.width / 2);
                startY = (int) (size.height * 0.30);
                endX = startX;
                endY = (int) (size.height * 0.70);
                break;

            case UP:
                startX = (int) (size.width / 2);
                startY = (int) (size.height * 0.70);
                endX = startX;
                endY = (int) (size.height * 0.30);
                break;

        }

        System.out.println("swiping from (" + startX + "," + startY + ") to (" + endX + "," + endY + ")");

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(point(endX, endY))
                .release()
                .perform();
    }
    public void spinAt(MobileElement element) {
        spinAt(element, -1);
    }

    /**
     * 等待element显示在页面上，直到超时或者已成功显示
     * @param element 需要等待的组件
     * @param timeout 单位秒，如果为-1则无限等待
     * @author zmt
     */
    public void spinAt(MobileElement element, int timeout) {
        int sleptTime = 1;
        sleep(1);
        while (!element.isDisplayed()) {
            if(sleptTime > timeout && timeout != -1) break;
            sleep(1);
            sleptTime++;
        }
    }
    public MobileElement findElementInWholePage(String xPath) {
        while(true) {
            MobileElement element = findElementByXPath(xPath, 2);
            if(element==null) {
                swipeTo(DIRECTION.UP);
            }
            else {
                return element;
            }
        }
    }
    public MobileElement findElementByXPath(String xPath, int timeout){
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            return driver.findElementByXPath(xPath);
        }catch(NoSuchElementException e) {
            return null;
        }
    }

    public MobileElement findElementByXPath(String xPath, int timeout, Map<String,String> filter){
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            List<MobileElement> elements = driver.findElementsByXPath(xPath);
            for(int i = 0; i < elements.size(); i++) {
                boolean isValid = true;
                for(String key : filter.keySet()) {
                    if(!elements.get(i).getAttribute(key).equals(filter.get(key))){
                        isValid = false;
                        break;
                    }
                }
                if(isValid) return elements.get(i);

            }
            return null;
        }catch(NoSuchElementException e) {
            return null;
        }
    }

    public MobileElement findElementByAccessibilityId(String accessibilityID,int timeout){
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            return driver.findElementByAccessibilityId(accessibilityID);
        }catch(NoSuchElementException e) {
            return null;
        }
    }
}
