package report;

import org.json.JSONObject;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class testReporter implements IReporter {

        private String content;
        private String otaTable;
        private String batTable;
        private String logTable;
        private String title;
        private String productName;
        private String version;
        private String testResult;
        private String total;
        private String pass;
        private String fail;
        private String skip;
        private String testOutputDir;
        private String testLocalStorage;
        private String sn;
        private String uid;
        private String nid;
        private Boolean sendEmail;
        private Map<String, Integer> testCaseCount = new HashMap<>();
        private Date today = new Date();
        StringBuilder contenttable = new StringBuilder("");


        @Override
        public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {
//        System.out.println("test over");
//    }
//    {

            title = null;
            content = "hi,all\n\n\t";
            System.out.println("Report called");
            content = this.content;
            contenttable.append("<table border=\"1\" style=\"width:1460px; height:765px;border:solid 1px #E8F2F9;font-size=12px;text-align:center;\">");
            contenttable.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th rowspan=\"1\"></th>" +
                    "<th rowspan=\"1\">Case</th>" + "<th rowspan=\"1\">Function</th>" + "<th rowspan=\"1\">Notes</th>" + "<th rowspan=\"1\">Result</th>" + "<th rowspan=\"1\">开始时间</th>" + "<th rowspan=\"1\">结束时间</th></tr>");
            List<ITestResult> testlist = new ArrayList<>();
            list1.forEach(suite -> {
                System.out.println("报告输出路径：" + suite.getOutputDirectory());
                suite.getResults().values().forEach(iSuiteResult -> {

                    ITestContext iTestContext = iSuiteResult.getTestContext();
                    IResultMap passedTests = iTestContext.getPassedTests();
                    pass = new String(String.valueOf(passedTests.size()));
                    IResultMap failedTests = iTestContext.getFailedTests();
                    fail = new String(String.valueOf(failedTests.size()));
                    IResultMap skippedTests = iTestContext.getSkippedTests();
                    skip = new String(String.valueOf(skippedTests.size()));
                    total = new String(String.valueOf(passedTests.size() + failedTests.size() + skippedTests.size()));
                    IResultMap failedConfig = iTestContext.getFailedConfigurations();

                    testlist.addAll(this.listTestResult(passedTests));
                    testlist.addAll(this.listTestResult(failedTests));
                    testlist.addAll(this.listTestResult(skippedTests));
                    testlist.addAll(this.listTestResult(failedConfig));
                });
            });
            this.sort(testlist);
            this.setEmailContext(testlist);
            content = content.replace("\t", "&emsp;");
            content = content.replace("\n", "<br>");
            content = content.replace(" ", "&nbsp;");
            content = content.replace("!", " ");
            System.out.println(content);
            contenttable.append("</table>");
            try {
                writeJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void writeJson() throws IOException {
            HashMap<String, Object> person = new HashMap<String, Object>();
            person.put("title", title);
            person.put("content", content);
            person.put("contenttable", contenttable.toString());
            person.put("file", testOutputDir + File.separator.toString() + "report" + File.separator.toString() + "emailable-report.html");
//        person.put("sendEmail", sendEmail.toString());
            String s = new JSONObject(person).toString();
            String Path = "email.json";
            System.out.println(Path);
            FileOutputStream fos = new FileOutputStream(Path);
            OutputStreamWriter os = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter w = new BufferedWriter(os);
            w.write(s);
            w.close();
        }
//        try {
//            EmailUtils.SendEmailInfoUser("panzhiwei@roborock.com", this.title, this.content, "panzhiwei@roborock.com,", new File("D:\testOTABAT\test-output\\emailable-report.html"));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }


        private String statusNumToChar(int statusNum) {
            switch (statusNum) {
                case 1:
                    return "SUCCESS";
                case 2:
                    return "FAILURE";
                case 3:
                    return "SKIP";
            }
            return null;
        }


        private ArrayList<ITestResult> listTestResult(IResultMap resultMap) {
            Set<ITestResult> results = resultMap.getAllResults();
            return new ArrayList<>(results);
        }

        private String formatDate(long date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(date);
        }

        private void sort(List<ITestResult> list) {
            Collections.sort(list, new Comparator<ITestResult>() {
                @Override
                public int compare(ITestResult r1, ITestResult r2) {
                    if (r1.getStartMillis() > r2.getStartMillis()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }

        private void getTestCaseCount(List<ITestResult> testlist) {
            for (ITestResult iTestResult : testlist) {
                IClass testClass = iTestResult.getTestClass();
                if (this.testCaseCount.containsKey(testClass.getName())) {
                    Integer count = this.testCaseCount.get(testClass.getName());
                    count += 1;
                    this.testCaseCount.put(testClass.getName(), count);
                } else {
                    this.testCaseCount.put(testClass.getName(), 1);
                }
            }
        }

        private void setEmailContext(List<ITestResult> testlist) {
            testResult = "";
            getTestCaseCount(testlist);
            System.out.println(testCaseCount);
            testlist.forEach(iTestResult -> {
                IClass testClass = iTestResult.getTestClass();
                String description = iTestResult.getMethod().getDescription();
                Object[] params = iTestResult.getParameters();
                if (iTestResult.getName().equals("getPackageForRequest")) {
                    this.productName = params[0].toString();
                    this.testOutputDir = params[7].toString();
                    ITestContext context = (ITestContext) params[params.length - 1];
                    ISuite suite = context.getSuite();
                   /* productPackage = (ProductPackage) suite.getAttribute("productPackage");
                    version = productPackage.getVersion();*/
                }
                if ("logsDownload".equals(iTestResult.getName())) {
                    testLocalStorage = params[9].toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    this.testLocalStorage += "\\";
                    this.testLocalStorage += dateFormat.format(new Date().getTime());
                }
                if ("forceUpdateToRobot".equals(iTestResult.getName())) {
                    sn = params[1].toString();
                    uid = params[2].toString();
                    nid = params[3].toString();
                }
                testResult = testResult + iTestResult.getName().toString() + "(" + description + ")";
                testResult = testResult + " - ";
                testResult = testResult + this.statusNumToChar(iTestResult.getStatus());
                testResult = testResult + "\n\n\t\t\t";
                testResult = testResult + "开始时间：" + this.formatDate(iTestResult.getStartMillis());
                testResult = testResult + "\n\t\t\t";
                testResult = testResult + "结束时间：" + this.formatDate(iTestResult.getEndMillis());
                testResult = testResult + "\n\n\t\t";
                List<String> statusColor = Arrays.asList("#92CF84", "#BA3537", "#94B0F0");
                if ("apptest.ota.test.IntranetOTATest".equals(testClass.getName())) {
                    contenttable.append("<tr>");
                    if (testlist.indexOf(iTestResult) == 0) {
                        String rowspan = testCaseCount.get(testClass.getName()).toString();
                        contenttable.append("<td rowspan=\"" + rowspan + "\" colspan=\"1\">OTATest</td>" +
                                "<td rowspan=\"" + rowspan + "\" colspan=\"1\">前置条件-OTA</td>");
                    }
//                if ("sendUpdateRequest".equals(iTestResult.getName().toString())){
//                    sendEmail = this.statusNumToChar(iTestResult.getStatus())=="SUCCESS";
//                }
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + iTestResult.getName().toString() + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + description + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\" bgcolor=\"" + statusColor.get(iTestResult.getStatus() - 1) + "\">" + this.statusNumToChar(iTestResult.getStatus()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getStartMillis()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getEndMillis()) + "</td>");
                    contenttable.append("</tr>");
                }
                if ("apptest.ota.test.OTATest".equals(testClass.getName())) {
                    contenttable.append("<tr>");
                    if (testlist.indexOf(iTestResult) == 0) {
                        String rowspan = testCaseCount.get(testClass.getName()).toString();
                        contenttable.append("<td rowspan=\"" + rowspan + "\" colspan=\"1\">MIOTA</td>" +
                                "<td rowspan=\"" + rowspan + "\" colspan=\"1\">前置条件-OTA</td>");
                    }
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + iTestResult.getName().toString() + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + description + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\" bgcolor=\"" + statusColor.get(iTestResult.getStatus() - 1) + "\">" + this.statusNumToChar(iTestResult.getStatus()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getStartMillis()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getEndMillis()) + "</td>");
                    contenttable.append("</tr>");
                }
                if ("apptest.ota.test.BATTest".equals(testClass.getName())) {
                    contenttable.append("<tr>");
//                ITestResult lastCase = testlist.get(testlist.indexOf(iTestResult)-1);
//                IClass lastCaseClass = lastCase.getTestClass();
//                System.out.println("last");
//                System.out.println(lastCaseClass.getName());
//                System.out.println("last");
                    if (testlist.indexOf(iTestResult) == testCaseCount.get("apptest.ota.test.IntranetOTATest")) {
                        String rowspan = testCaseCount.get(testClass.getName()).toString();
                        contenttable.append("<td rowspan=\"" + rowspan + "\" colspan=\"1\">BATTest</td>" +
                                "<td rowspan=\"" + rowspan + "\" colspan=\"1\">BAT执行</td>");
                    }

                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + iTestResult.getName().toString() + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + description + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\" bgcolor=\"" + statusColor.get(iTestResult.getStatus() - 1) + "\">" + this.statusNumToChar(iTestResult.getStatus()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getStartMillis()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getEndMillis()) + "</td>");
                    contenttable.append("</tr>");
                }
                if ("apptest.ota.test.LogTest".equals(testClass.getName())) {
                    contenttable.append("<tr>");
                    if (testlist.indexOf(iTestResult) == testCaseCount.get("apptest.ota.test.BATTest") + testCaseCount.get("apptest.ota.test.IntranetOTATest")) {
                        String rowspan = testCaseCount.get(testClass.getName()).toString();
                        contenttable.append("<td rowspan=\"" + rowspan + "\" colspan=\"1\">LogTest</td>" +
                                "<td rowspan=\"" + rowspan + "\" colspan=\"1\">Log下载</td>");
                    }

                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + iTestResult.getName().toString() + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + description + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\" bgcolor=\"" + statusColor.get(iTestResult.getStatus() - 1) + "\">" + this.statusNumToChar(iTestResult.getStatus()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getStartMillis()) + "</td>");
                    contenttable.append("<td rowspan=\"1\" colspan=\"1\">" + this.formatDate(iTestResult.getEndMillis()) + "</td>");
                    contenttable.append("</tr>");
                }
            });
            this.content += this.productName;
            this.content += " ";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.content += dateFormat.format(this.today);
            this.content += " OTABAT测试\n\t";
            this.title = this.productName + " " + "OTABAT" + " " + dateFormat.format(this.today) + "";
//        this.content += "\n测试详情数据和清扫记录截图请访问 <a!href=\"http://192.168.98.32:8080\">http://192.168.98.32:8080/</a> 查看";
            this.content += "\n\t测试版本：" + version;
            this.content = this.content + "\n\t机器SN：" + this.sn;
            this.content = this.content + "\n\tUID：" + this.uid;
            if (nid != "") {
                this.content = this.content + "\n\t匿名化ID：" + this.nid;
            }
            this.content = this.content + "\n\tlog：" + this.testLocalStorage.toString();
            this.content += "\n\t测试结果如下：";
            this.content = this.content + "\n\n\t\t总数:" + total + ", Pass:" + pass + ", Fail:" + fail + ", Skip:" + skip + "\n\n\t\t";
//        this.content += testResult;
        }
    }