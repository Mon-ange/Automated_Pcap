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
import pages.mihome.plugin.hint.StartUpHints;
import pages.roborock.hint.PrivacyAgreementHintPage;
import pages.roborock.mainpage.LoginedRoborockMainPage;
import pages.roborock.mainpage.initialization.RoborockInitializationPage;
import utils.PacketCapturer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoborockCapturePackageTest {
    private static Logger logger = LoggerFactory.getLogger(MihomeCapturePackageTest.class);

    private Path outputPath;

    @BeforeClass
    @Parameters("pcap-output-path")
    public void setup(String pcapOutputPath) throws IOException, InterruptedException {
        System.out.println("Roborock Capture Package Test Suite setting up....");
        //0.创建抓包结果目录
        createPcapDirectory(pcapOutputPath);
        //1.log in
        new RoborockInitializationPage().initialize().clickLogin()
                .login(Globals.params.get("rr.username"),Globals.params.get("rr.password"));
    }

    @BeforeMethod
    public void reboot() {
        //System.out.println("Test Case Setup...");
        Globals.driver.closeApp();
        Globals.driver.launchApp();

        //石头会自己发现新设备，在每次launchapp时弹出，点击空白处取消

    }

    @Test
    @Parameters("productName")
    public void addDeviceTest(String productName) throws PcapNativeException, NotOpenException, InterruptedException {//快连添加设备
        //start the capture
        //System.out.println("Add Device Test Starting Capture Package...");
        //System.out.println("output path: " + outputPath.toString());
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("addDevice.pcap").toString());
        packetCapturer.startCapture();
        //System.out.println("Add Device Test Starting...");
        //start the test case
        new LoginedRoborockMainPage().clickAddDevice(productName)
                .connectWifi(Globals.params.get("wifiPwd"));
        new PrivacyAgreementHintPage().agreePrivacy();
        new StartUpHints().acceptAllHints();
        //stop the capture
        packetCapturer.stopCapture();
    }

    @Test
    public void downloadVoiceTest() throws PcapNativeException, NotOpenException, InterruptedException {//下载语音包
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("downloadVoice.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().clickSoundPackage().clickAll();
        packetCapturer.stopCapture();
    }

    @Test
    public void productGuideTest() throws PcapNativeException, NotOpenException, InterruptedException {//产品百科
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("productGuide.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().clickproductGuide().goBack();
        packetCapturer.stopCapture();
    }

    @Test
    public void normalCleanTest() throws PcapNativeException, NotOpenException, InterruptedException {//正常清扫
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("normalClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().doClean();
        packetCapturer.stopCapture();
    }

    @Test
    public void updatedCleanTest() throws PcapNativeException, NotOpenException, InterruptedException {//正常清扫
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("afterClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().doClean();
        packetCapturer.stopCapture();
    }

    @Test
    public void firmwareUpdateTest() throws PcapNativeException, NotOpenException, InterruptedException {//固件升级
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("firmwareUpdate.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().clickFirmwareUpdate().goBack();
        packetCapturer.stopCapture();
    }

    @Test
    public void maintenanceTest() throws PcapNativeException, NotOpenException, InterruptedException {//耗材维护
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("maintenance.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().checkSupplies().checkAll();
        packetCapturer.stopCapture();
    }

    @Test
    public void errorImageTest() throws PcapNativeException, NotOpenException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("errorImage.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().makeError();
        packetCapturer.stopCapture();
    }

    @Test
    public void sheduledCleanTest() throws NotOpenException, PcapNativeException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("sheduledClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().clickTimer().startTimer().confirmTimer().backToSpec().dockBack();
        packetCapturer.stopCapture();
    }

    @Test
    public void remoteControlTest() throws NotOpenException, PcapNativeException, InterruptedException {
        PacketCapturer packetCapturer = new PacketCapturer(outputPath.resolve("remoteClean.pcap").toString());
        packetCapturer.startCapture();
        new LoginedRoborockMainPage().clickSpecificDevice().clickSetting().clickRemote();
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
