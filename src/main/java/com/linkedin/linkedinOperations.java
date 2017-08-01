package com.linkedin;

import com.linkedin.pages.linkedinPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.linkedin.steps.linkedinSteps.Credentials;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class linkedinOperations {

    public static linkedinPage loginins1 = null;
    public static linkedinPage loginins2 = null;
    public static linkedinPage loginins3 = null;


    public static List<String> jukoList(WebDriver driver, linkedinPage loginins1) {
        List<String> jukoTemp = new ArrayList<String>();
        driver.get("http://jobs.zhuko.net/");
        int z = loginins1.jukoJobsCount();

        for (int z2 = 0; z2 < 1; z2 = z2 + 1) { //c2
            jukoTemp.add(driver.findElements(By.xpath(loginins1.jukoXpath)).get(z2).getText().substring(0, driver.findElements(By.xpath(loginins1.jukoXpath)).get(z2).getText().indexOf("#")));
            System.out.println("Vacancies from zhuko " + jukoTemp.get(z2));

        }
        return jukoTemp;
    }

    public static void linkedinLogin(WebDriver driver,Credentials cred) {
        driver.get("https://www.linkedin.com/");



        driver.findElement(By.name("session_key")).clear();
        driver.findElement(By.name("session_key")).sendKeys(cred.login);
        driver.findElement(By.name("session_password")).clear();
        driver.findElement(By.name("session_password")).sendKeys(cred.password);
        driver.findElement(By.id("linkedinPage-submit")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //String redirected_url = driver.getCurrentUrl();//This url is not matching with the one  showing in address bar.
        //driver.get(redirected_url);


    }

    public static Credentials readIniFile(String filename)
    {
        Credentials cred=null;
        File f = new File(filename);
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine;
            System.out.println("Reading file " + filename);
            if ((readLine = b.readLine()) != null) {
                cred.login= readLine;
            }
            if ((readLine = b.readLine()) != null) {
                cred.password = readLine;
            }
            if ((readLine = b.readLine()) != null) {
               cred.keyword = readLine;
            }
        } catch (Exception ex) {
            System.out.println("File read failed " + filename);
        }
        return cred;
    }
    public static void mynetworkScan(WebDriver driver, WebDriver driver2) {
        int start = 0;
        int numAdded = 0;
        driver.get("https://www.linkedin.com/mynetwork/");
        //((JavascriptExecutor)driver).executeScript("window.open()");
        //ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        for (int xjp = 0; xjp < 10; xjp = xjp + 1) { //cycle
            utils.sleepThread(3500);
            utils.scrollDown(driver);
            System.out.println("Scroll num " + xjp);
        }

        for (int xjp = 0; xjp < 10000; xjp = xjp + 1) { //cycle
            for (int xj2 = start; xj2 < driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).size(); xj2 = xj2 + 1) { //cycle

                if ((((driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).get(xj2).getText().indexOf("ecru") >= 0) || (driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).get(xj2).getText().indexOf("goLang") >= 0)) || (driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).get(xj2).getText().indexOf("HR") >= 0)) || (driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).get(xj2).getText().indexOf("ooking") >= 0)) {
                    System.out.println("View " + driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).get(xj2).getText());
                    System.out.println("View2 " + driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]/..")).get(xj2).getAttribute("href"));
                   String newUrl = driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]/..")).get(xj2).getAttribute("href");
                    //          driver.switchTo().window(tabs.get(1));
                    //driver.get("https://www.news.google.com");
                    System.out.println("View2 3 " + newUrl);
                    try {
                         driver2.get(newUrl);
                    } catch (Exception ex) {
                        System.out.println("View2 fail " + ex.getMessage());
                    }
                    try {

                        loginins2.connectClick();
                        utils.sleepThread(500);
                        loginins2.sendNowClick();
                        utils.sleepThread(5000);
                        numAdded++;
                        //  driver.switchTo().window(tabs.get(0));
                        System.out.println("Adding item number is " + numAdded);
                    } catch (Exception ex) {
                        System.out.println("Problem during adding " + ex.getMessage());
                    }
                }

            }
            start = driver.findElements(By.xpath("//a[contains(@class,'mn-person-info__link')]/span[contains(@class,'mn-person-info__occupation')]")).size();
            utils.scrollDown(driver);
        }
    }

    public static void recruitCycle(WebDriver driver, WebDriver driver2, WebDriver driver3, int page) {
        loginins1 = new linkedinPage(driver);
        loginins2 = new linkedinPage(driver2);
        loginins3 = new linkedinPage(driver3);
        for (int xj = 0; xj < 10000; xj = xj + 1) { //cycle
            //625
            driver.get("https://www.linkedin.com/recruiter/smartsearch?searchHistoryId=1909582134&searchCacheKey=ff3208d1-2429-4475-b085-4055e6ff0c05%2CZFrx&linkContext=Controller%3AsmartSearch%2CAction%3Asearch%2CID%3A1909582134&doExplain=false&start=" + ((page * 25)));
            for (int xj2 = 0; xj2 < driver.findElements(By.xpath("//h3[@class='name']/a")).size(); xj2 = xj2 + 1) {
                driver2.get(loginins1.getRecruitmentName(xj2));
                utils.sleepThread(2000);
                driver3.get(loginins2.getRecruitProfileLink());
                if (driver3.findElements(By.xpath("//span[text()='Connect']")).size() > 0) {
                    loginins3.connectClick();
                    utils.sleepThread(500);
                    loginins3.sendNowClick();
                    utils.sleepThread(500);
                }
            }
        }
    }
}
