package com.linkedin.steps;

import com.linkedin.pages.linkedinPage;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.linkedin.utils;
import com.linkedin.linkedinOperations;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import java.util.StringTokenizer;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.List;


public class linkedinSteps {

    public class Credentials {
        public String login;
        public String password;
        public String keyword;
    }
    public static String arg = "";
    public static String drv = "";
    public static String jukoFlag = "";
    public static String message = "";
    public static String recruitFlag = "";
    public static String distance = "";
    public static String ignoreCity = "";
    public static String myNetwork = "";
    public static String viewOnly = "";
    private static WebDriver driver = null;
    private static WebDriver driver2 = null;
    private static WebDriver driver3 = null;
    private static String baseUrl = "";
    private static linkedinPage loginins1 = null;
    private static linkedinPage loginins2 = null;


    @Given("Selenium started in $browserstack with URL $url $key")
    public void givenDriverStarted(String startKind, String url, String key) {
        String changedUrl = url;
        if (distance.equalsIgnoreCase("2")) {
            changedUrl = changedUrl.replace("?facetNetwork=%5B\"F\"%5D", "?facetNetwork=%5B\"S\"%5D");
        }
        if (startKind.equalsIgnoreCase("localpage")) {
            String path = System.getProperty("user.dir" + "");
            System.out.println("Path for results and Firefox driver is " + path);
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--enable-strict-powerful-feature-restrictions");
            options.addArguments("disable-web-security");
            options.addArguments("disable-extensions");
            options.addArguments("--test-type --no-sandbox");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            if (drv.equalsIgnoreCase("c")) {
                driver = new ChromeDriver(capabilities);
                driver2 = new ChromeDriver(capabilities);
                driver3 = new ChromeDriver(capabilities);
            }
            if (drv.equalsIgnoreCase("f")) {
                //    System.setProperty("webdriver.edge.driver", path + "\\MicrosoftWebDriver.exe");
                //    System.setProperty("webdriver.gecko.driver", path + "\\geckodriver.exe");
                //   DesiredCapabilities cap = new DesiredCapabilities();
                //  cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                //   capabilities.setCapability("marionette", false);
                driver = new FirefoxDriver();
                driver2 = new FirefoxDriver();
                driver3 = new FirefoxDriver();
            }
            try {
                System.out.println("Page init start base" + baseUrl);
                loginins1 = new linkedinPage(driver);
                loginins2 = new linkedinPage(driver2);

            } catch (Exception ex) {
                System.out.println("Page init failed " + ex.getMessage());
            }
            List<String> juko = new ArrayList<String>();
            if (jukoFlag.equalsIgnoreCase("yes")) { //load vacancies
                juko = linkedinOperations.jukoList(driver, loginins1);
            }

            Credentials cred;
            cred=linkedinOperations.readIniFile(path + "\\log.txt");
             linkedinOperations.linkedinLogin(driver,cred);
            linkedinOperations.linkedinLogin(driver2,cred);
             linkedinOperations.linkedinLogin(driver3,cred);
            juko.add(cred.keyword);
            utils.sleepThread(1000);
            if (recruitFlag.equalsIgnoreCase("yes")) { //rec
                linkedinOperations.recruitCycle(driver, driver2, driver3, Integer.valueOf(arg));
            } //recr

            if (myNetwork.equalsIgnoreCase("yes")) { //rec
                linkedinOperations.mynetworkScan(driver, driver2);
            } //re
            int num = 0;
            boolean ends = false;
            for (int xj = 0; xj < 1000 && !ends; xj = xj + 1) { //cycle
                String keys = juko.get(xj);
                String keys2 = "";
                StringTokenizer st = new StringTokenizer(keys);
                while (st.hasMoreElements()) {
                    keys2 = keys2 + st.nextElement();
                    if (st.hasMoreElements()) {
                        keys2 = keys2 + "+";
                    }

                }
                key = keys2 + "&origin=FACETED_SEARCH" + "" + "&page=";
                List<String> outc = new ArrayList<String>();
                outc.add("name;tagline;desc;dist;url;contact;facebook-twitter;");
                for (int x = Integer.parseInt(arg); x < 10000 && !ends; x = x + 1) { //cycle
                    baseUrl = changedUrl.trim() + key.trim() + String.valueOf(1 + x).trim();
                    System.out.println("Baseurl is " + baseUrl);
                    driver.get(baseUrl);
                    utils.sleepThread(1000);
                    utils.writeTextToFile(outc, key + ".csv", path + "\\csv\\");
                    try {
                        if ((driver.findElements(By.xpath("//h1[contains(@class,'search-no')]")).size() == 1) || (driver.findElements(By.xpath("//p[contains(@class,'subline-level-2')]")).size() == 0)) {
                            ends = true;
                        }
                    } catch (Exception ex) {
                        System.out.println("Check the end of the loop failed with message " + ex.getMessage());
                    }
                    utils.scrollDown(driver);
                    for (int x2 = 1; x2 < 10 && !ends; x2 = x2 + 1) { //c2
                        try {
                            String dst = "";
                            try {
                                dst = loginins1.getDistLabel(x2);
                            } catch (Exception ex) {
                                System.out.println("Get distance failed with size  " + driver.findElements(By.xpath("//span[@class='dist-value']")).size() + " and with message " + ex.getMessage());
                            }
                            System.out.println("Distance is " + dst);
                            if (((dst.equalsIgnoreCase("1st")) || (dst.equalsIgnoreCase("2nd")))) {
                                String stra = "";
                                try {
                                    stra = driver.findElements(By.xpath("//p[contains(@class,'subline-level-2')]")).get(x2).getText();
                                } catch (Exception ex) {
                                    System.out.println("Get country failed with message " + ex.getMessage());
                                }
                                System.out.println("Country is " + stra);
                                if ((stra.contains("Russia")) || (stra.contains("Ukraine"))) {
                                    String contact;
                                    if (dst.equalsIgnoreCase("1st") && distance.equalsIgnoreCase("1")) {
                                        System.out.println("1st geturl " + loginins1.getHrefForContact(x2));
                                        driver2.get(loginins1.getHrefForContact(x2));
                                        try {
                                            loginins2.clickSeeMore();
                                        } catch (Exception ex) {
                                            System.out.println("Click failed with message " + ex.getMessage());
                                        }
                                        if (ignoreCity.equalsIgnoreCase("no")) {
                                            utils.scrollDown(driver2);
                                            utils.sleepThread(2000);
                                        }
                                        if ((driver2.findElements(By.xpath("//*[contains(text(),'Kiev')]")).size() > 0 && driver2.findElements(By.xpath("//*[contains(text(),'Kiev')]")).get(0).getText().contains("Kiev")) || ignoreCity.equalsIgnoreCase("yes")) { //ode
                                            num++;
                                            String ous = loginins1.getContactName(x2) + ";";
                                            ous = ous + loginins1.getOccupation(x2) + ";";
                                            ous = ous + loginins1.getCountry(x2) + ";";
                                            ous = ous + dst + ";";
                                            ous = ous + loginins1.getContactUrl(x2) + ";";
                                            if (message.equalsIgnoreCase("yes")) {
                                                try {
                                                    loginins1.clickMessageButton(x2);

                                                    loginins1.setMessageArea(" добрый день! ищу работу, добавьте, пожалуйста ,меня." + "\n");

                                                } catch (Exception ex) {
                                                    System.out.println("Message sending failed with message " + ex.getMessage());
                                                }
                                            }
                                            contact = loginins2.getContactListInDiv() + ";";
                                            contact = contact + loginins2.getContactListInLi() + ";";
                                            outc.add(ous + contact + ";");
                                            System.out.println("contact " + contact);
                                        }//od
                                    } //1st
                                    if ((dst.equalsIgnoreCase("2nd")) && (distance.equalsIgnoreCase("2"))) {
                                        System.out.println("get 2nd " + num + " " + loginins1.getHrefForContact(x2));
                                        if (!loginins1.isInviteSended(x2) || viewOnly.equalsIgnoreCase("yes")) {//inv
                                            driver2.get(loginins1.getHrefForContact(x2));
                                            if ((driver2.findElements(By.xpath("//*[contains(text(),'Odessa')]")).size() > 0) || (ignoreCity.equalsIgnoreCase("yes"))) { //city
                                                try {
                                                    if (viewOnly.equalsIgnoreCase("no")) {
                                                        loginins2.connectClick();
                                                        utils.sleepThread(500);
                                                        loginins2.sendNowClick();
                                                        utils.sleepThread(500);
                                                    }
                                                } catch (Exception ex) {
                                                    System.out.println("Connection failed with message " + ex.getMessage());
                                                }
                                                Logs log = driver.manage().logs();
                                                List<LogEntry> logsEntries = log.get("browser").getAll();
                                                List<String> lout = new ArrayList<String>();
                                                for (LogEntry entry : logsEntries) {
                                                    String lEnt = entry.getMessage();
                                                    System.out.println("Connect console log " + lEnt);
                                                    lout.add(lEnt);
                                                }
                                                utils.writeTextToFile(lout, "consolelog" + ".log", path + "\\csv\\");
                                            }//city
                                        }//vite
                                        //utils.sleepThread(2000);
                                    }//f
                                }
                            }
                        } catch (Exception ex2) {
                            System.out.println("Driver start2 failed " + ex2.getMessage());
                        }
                    }//c2
                } //cycle
                utils.writeTextToFile(outc, key + ".csv", path + "\\csv\\");
            }
            System.out.println("Apply configuration for " + baseUrl);

        }
        driver.quit();
        driver2.quit();
        driver3.quit();
    }


}



