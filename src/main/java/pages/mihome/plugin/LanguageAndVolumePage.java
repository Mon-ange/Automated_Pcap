package pages.mihome.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import org.apache.xmlbeans.impl.xb.xsdownload.DownloadedSchemaEntry;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import utils.AppiumUtil;
import pages.common.AppiumEnv;
import pages.common.Globals;
import pages.common.Page;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import io.appium.java_client.MobileElement;

public class LanguageAndVolumePage extends Page{
	public void clickAll() {
		Set<String> downloadedSet = new HashSet<>();
		while(true) {
			boolean allDownloaded = true;
			List<MobileElement> elements = getVoiceDownloadButtons();
			System.out.println(elements.size());
			for (MobileElement element: elements){
				String elementDesc = element.getAttribute("content-desc");
				if(!downloadedSet.contains(elementDesc)) {
					allDownloaded = false;
					clickDownload(element);
					downloadedSet.add(elementDesc);
				}
			}
			swipeTo(DIRECTION.UP);
			if(allDownloaded == true) break;
		}
		
	}
	
	private void clickDownload(MobileElement element) {
		String elementDesc = element.getAttribute("content-desc");
		System.out.println("Downloading Voice: " + elementDesc);
		sleep(1);
		element.click();
		for(int i = 0; i < 60; i++) {
			try{
				String text = driver.findElementByXPath("//android.view.ViewGroup[@content-desc='"+ elementDesc +"' and @index='4']")
						.findElementByClassName("android.widget.TextView").getText();
				if(text.equals("使用中")) return;
			}
			catch(Exception e){}
		}
	}


	
	private List<MobileElement> getVoiceDownloadButtons(){
		return driver.findElementsByXPath("//android.view.ViewGroup[contains(@content-desc,'sound_package_use_') and @index='4']");
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//unit test
		AppiumUtil.parseProperty("/appiumCapability.properties");
		Globals.env = new AppiumEnv();
		Globals.driver = Globals.env.setupDriver("Android",  "Redmi 5", true);
		new LoginedMihomeMainPage().clickSpecificDevice("Roborock S7 MaxV").clickSetting().clickSoundPackage().clickAll();
	}
	
	
}
