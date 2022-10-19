## 测试目的、方法及通过标准

### 目的

测试扫地机的安全风险：

* 防止扫地机在一个地区工作时，访问其他地区的网络
* 检查是否使用tls 1.2version 加密通信数据
* 检查是否不发送Standard query AAAA请求

### 方法

* 重置wifi，连接扫地机，执行所有和网络通信相关的操作，过程中抓包
* 分析数据包，检查源地址或目标地址是扫地机（固件隐私）或手机（插件隐私）的IP地址
### 通过标准

* 扫地机在一个地区工作时，不得访问其他地区的IP（使用工具https://10.2.16.214:8080/解析）

* 使用tls筛查，检查到使用tls 1.2version加密通信数据
* 使用dns过滤，检查未发送Standard query AAAA请求



## 主要功能

根据隐私抓包测试需求，需要对以下操作过程进行抓包

1. 快连成功添加机器人

2. 点击产品指南中每一项资源

3. 点击下载每一个语音包资源和试听资源

4. 插件上点击清扫按钮，让机器清扫1min，插件停留在插件主界面（使机器上传地图）

5. 机器做一次固件升级

6. 插件上点击清扫按钮，让机器清扫1min后回充，插件停留在插件主界面（使机器上传地图）

7. 添加一个服务器定时，并等待定时启动

8. 遥控扫地机器人清扫1min后回充，插件停留在插件主界面

9. 查看耗材每一项

10. 触发报错，点击进入详情页



## 环境准备

1. 一台PC

    * Windows安装WinPcap，Linux/Mac/UNIX安装libpcap，这是Pcap4J的native libraries

    * 预装jdk1.8或以上并配置环境变量

    * 预装node.js和appium

        * 安装appium前先安装node.js, 因为appium是使用node.js实现的，node是解释器

    * 预装adb并配置环境变量
2. 一台预装米家app、石头app的测试安卓机
3. 用PC创建热点，给扫地机和安卓机连接
    * 目的：保证扫地机和安卓机在一个局域网内



## 输入、运行及输出

### 输入
* 按照被测app修改properties的appPackage和appActivity变量
* 通过ipconfig命令查看pc创建热点网络的IPV6并修改config.properties对应变量
* 按照被测网络的ssid和pwd修改config.properties对应变量
* 按照被测米家账号和密码修改config.properties对应mihome.username及mihome.username（石头修改rr.username和rr.password)
* 按照要测的机器的上市名称或代号，如”石头自清洁扫拖机器人G10S"和机型所属地区，如"中国大陆",修改mihome_pcap.xml对应变量(石头修改上市名称即可，注意使用核心名称进行模糊搜索)
* **mihome_pcap.xml及roborock_pcap.xml里method每个条目对应一个用例**，可根据需要修改。因为每个用例都必须建立在添加设备用例基础上，如需进行单测，可进入每个用例的主page运行main方法（不重置设备，不需要重新连接）
### 运行

* 确保对扫地机重置wifi

* 确保appium已运行在4723端口（无需打开inspector）

* 右击mihome_pcap.xml(roborock_pcap.xml)运行
### 输出

* 抓包文件夹，包含有每个测试用例对应抓的包，于工程的根目录test-output/MICapturePackage下(石头：test-output/RRCapturePackage)，如需修改，修改mihome_pcap.xml(roborock_pcap.xml)相关parameter
* 本次自动化测试报告，由testng自动生成，每次运行覆盖，test-output/MICapturePackage/MihomeCapturePackageTest.html

## 维护须知
* MihomeCapturePackageTest和RoborockCapturePackageTest是顶层业务逻辑层，关注业务，不要在里面写跟业务无关的逻辑例如sleep(),findElement()等
* 尽量避免xpath定位，有accessibilyid就使用

## 抓包项目杂项
* 因为米家添加设备名字精准，石头的设备名称比较随意，例如同一个产品米家叫“石头自清洁扫拖机器人G10S”，石头上在添加时叫“石头 G10S-miapC005”，故代码针对米家的设备名称是精准匹配，石头是部分匹配，所以在修改配置文件时候，对于roborock_pcap.xml里修改名称时尽量写核心一点，例如”G10S"
* 将手机和扫地机的ip写在抓包文件夹里写成ip.txt，防止分析人员混淆ip
* 每次抓包时按照需求把扫地机刷机及改model到适应测试需求
* 运行本自动化测试前，手动重置扫地机wifi

## shell命令
* 获取appPackage
```shell
adb shell pm list package -f | findstr "xiaomi"
package:/data/app/com.xiaomi.smarthome-i1575Xy9FRWRR-QCc1xrCA==/base.apk=com.xiaomi.smarthome
```
* 获取appActivity  
  首先打开米家app
```shell
adb shell dumpsys window w | findstr mCurrent
  mCurrentFocus=Window{ec919b8 u0 com.xiaomi.smarthome/com.xiaomi.smarthome.SmartHomeMainActivity}
```

## 米家账号备份
mihome.username=1155970109  
mihome.password=hu86505721111
mihome.username=capturepackage-cntest@roborock.com
mihome.password=Mijia20220011
## 石头账号备份
rr.username=commit-test@roborock.com
rr.password=Commit_2022  
rr.username=capturepackage-cntest@roborock.com
rr.password=Pcapcntest21
## 米家测试机器名称备份
石头自清洁扫拖机器人G10S  
Roborock S7 MaxV