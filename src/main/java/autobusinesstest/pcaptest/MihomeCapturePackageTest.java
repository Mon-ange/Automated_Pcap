package autobusinesstest.pcaptest;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.common.Globals;
import pages.mihome.hint.AuthorityPages;
import pages.mihome.hint.MihomeAllowLoacatingHintPage;
import pages.mihome.hint.PluginPrivacyAgreementHint;
import pages.mihome.mainpage.LoginedMihomeMainPage;
import pages.mihome.mainpage.device.MoreSettingsPage;
import pages.mihome.mainpage.device.SetDeviceNamePage;
import pages.mihome.mainpage.device.SetRoomPage;
import pages.mihome.mainpage.device.ShareWithFamiliesPage;
import pages.mihome.mainpage.initialization.MihomeInitializationPage;
import pages.mihome.plugin.hint.StartUpHints;
import utils.PacketCapturer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MihomeCapturePackageTest {
    private static Logger logger = LoggerFactory.getLogger(MihomeCapturePackageTest.class);

    private Path outputPath;

    @BeforeClass
    @Parameters({"pcap-output-path","productLocation"})
    public void setup(String pcapOutputPath,String productLocation) throws IOException, InterruptedException {
        System.out.println("Mihome Capture Package Test Suite setting up....");
        //0.创建抓包结果目录
        createPcapDirectory(pcapOutputPath);
        //1.log in
        new MihomeInitializationPage().initialize()
                .clickLogin()
                .login(Globals.params.get("mihome.username"), Globals.params.get("mihome.password"),productLocation);
        //2.处理权限及引导弹窗
        //new CancelledUpdatePages().cencelledUpdateApp();

        new MihomeAllowLoacatingHintPage().finishHint();
        new AuthorityPages().allowLocatingInUse();
    }

    @BeforeMethod
    public void reboot() {
        //System.out.println("Test Case Setup...");
        Globals.driver.closeApp();
        Globals.driver.launchApp();
    }

    @Test
    @Parameters("productName")
    public void addDeviceTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//快连添加设备
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("addDevice.pcap").toString());
        packetCapturer.startCapture();

        //start the test case
        new LoginedMihomeMainPage().clickAddDevice().clickSearch().searchProduct(productName)
                .clickReseted().connectWifi(Globals.params.get("ssid"),Globals.params.get("wifiPwd"));
        //以下几个米家组件随版本更新经常变化，按需要注释
        new SetRoomPage().setRoom();
        new SetDeviceNamePage().setDeviceName();
        new MoreSettingsPage().setMore();
        //new IntelligentScenePage().setIntelligentScene();
        new ShareWithFamiliesPage().doNotShare();
        new PluginPrivacyAgreementHint().agreePluginPrivacy();

        //以下是抽象的石头组件，随版本更新经常变化，按需改变accessid
        new StartUpHints().acceptAllHints();

        //stop the capture
        packetCapturer.stopCapture();

    }

    @Test
    @Parameters("productName")
    public void downloadVoiceTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//下载语音包
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("downloadVoice.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().clickSoundPackage().clickAll();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void productGuideTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//产品百科
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("productGuide.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().clickproductGuide().goBack();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void normalCleanTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//正常清扫
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("normalClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).doClean();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void updatedCleanTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//正常清扫
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("updatedClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).doClean();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void firmwareUpdateTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//固件升级
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("firmwareUpdate.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().clickFirmwareUpdate().immediatelyUpdate().goBack();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void maintenanceTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//耗材维护
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("maintenance.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().checkSupplies().checkAll();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void errorImageTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("errorImage.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).makeError();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void sheduledCleanTest(String productName) throws NotOpenException, PcapNativeException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("sheduledClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().clickTimer().startTimer().confirmTimer().backToSpec().dockBack();
        packetCapturer.stopCapture();
    }

    @Test
    @Parameters("productName")
    public void remoteControlTest(String productName) throws NotOpenException, PcapNativeException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("remoteControl.pcap").toString());
        packetCapturer.startCapture();
        new LoginedMihomeMainPage().clickSpecificDevice(productName).clickSetting().clickRemote().remote();
        packetCapturer.stopCapture();
    }
    private void createPcapDirectory(String path) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String outputDir = "pcap-test" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").format(now);
        outputPath = Paths.get(path).resolve(outputDir);
        //System.out.println("outputPath: " + outputPath.toString());
        Path absolutePath = Files.createDirectories(outputPath).toAbsolutePath();
        // System.out.println("absolutePath" + absolutePath.toString());
    }
}
