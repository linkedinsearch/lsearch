package com.linkedin.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.*;


public class linkedinPage {

    /**
     * All WebElements are identified by @FindBy annotation
     */
    WebDriver driver;
////*[@id="ember2759"]/div[1]/div/section/div/header/button/span/li-icon/svg
    public static String jukoXpath = "//div[@class='col']/h2/a";

    @FindBy(xpath = "//div[@class='col']/h2/a")
    private static WebElement jukoJobs;


    @FindBy(xpath = "//button[contains(@data-control-name,'contact_see_more')]/span[contains(@aria-hidden,'true')]/span")
    private static WebElement seeMoreLabel;

    @FindBy(xpath = "//textarea[@name='message']")
    private static WebElement messageArea;

    @FindBy(xpath = "//span[text()='Connect']")
    private static WebElement connectButton;

    @FindBy(xpath = "//button[@class='button-primary-large ml3']")
    private static WebElement sendNowButton;

    @FindBy(xpath = "//li[contains(@class,'public-profile searchable')]/a")
    private static WebElement recruitProfileLink;

    @FindBy(xpath = "//h3[@class='name']/a")
    WebElement recruitContactNameLink;


    public linkedinPage(WebDriver driver) {

        this.driver = driver;
        //This initElements method will create  all WebElements
        PageFactory.initElements(driver, this);
    }

    public String getRecruitProfileLink() {
        return recruitProfileLink.getAttribute("href");
    }

    public void clickSeeMore() {
        waiting(seeMoreLabel);
        seeMoreLabel.click();
    }

    public void connectClick() {
        waiting(connectButton);
        connectButton.click();
    }

    public void sendNowClick() {
        waiting(sendNowButton);
        sendNowButton.click();
    }

    public String getDistLabel(int num) {

        return driver.findElements(By.xpath("//span[@class='dist-value']")).get(num).getText();
    }

    public String getContactListInDiv() {
        String tmpContact = "";
        int sz = driver.findElements(By.xpath("//div[contains(@class,'pv-contact')]")).size();
        for (int xc = 0; xc < sz; xc = xc + 1) {

            tmpContact = tmpContact + " " + driver.findElements(By.xpath("//div[contains(@class,'pv-contact')]")).get(xc).getText();
        }
        return tmpContact;
    }


    public String getContactListInLi() {
        String tmpContact = "";
        int sz = driver.findElements(By.xpath("//li[contains(@class,'pv-contact')]")).size();
        for (int xc = 0; xc < sz; xc = xc + 1) {

            tmpContact = tmpContact + " " + driver.findElements(By.xpath("//li[contains(@class,'pv-contact')]")).get(xc).getText();
        }
        return tmpContact;


    }

    public boolean isInviteSended(int num) {
        boolean sended=false;
        try {
            sended = driver.findElements(By.xpath("//div[contains(@class,'search-result__info')]/../div[contains(@class,'search-result__acti')]/div//button")).get(num).getText().indexOf("vite") > 0;
        }
        catch (Exception ex)
        {
            System.out.println("Invite sending check failed "+ex.getMessage());
        }
        return sended;
    }


    public void setMessageArea(String text) {
        waiting(messageArea);
        messageArea.clear();
        messageArea.sendKeys(text);
    }

    public String getContactName(int num) {
        return driver.findElements(By.xpath("//span[@class='name actor-name']")).get(num).getText();
    }

    public String getOccupation(int num) {
        return driver.findElements(By.xpath("//p[contains(@class,'subline-level-1')]")).get(num).getText();
    }

    public String getCountry(int num) {
        return driver.findElements(By.xpath("//p[contains(@class,'subline-level-2')]")).get(num).getText();
    }

    public String getContactUrl(int num) {
        return driver.findElements(By.xpath("//div[contains(@class,'search-result__info')]/a[@data-control-name='search_srp_result']")).get(num).getAttribute("href");
    }

    public void clickMessageButton(int num) {
        driver.findElements(By.xpath("//div[contains(@class,'search-result__info')]/../div[contains(@class,'search-result__acti')]/div//button")).get(num).click();
    }

    public int jukoJobsCount() {
        return driver.findElements(By.xpath(jukoXpath)).size();
    }

    public String getHrefForContact(int num) {
        return driver.findElements(By.xpath("//div[contains(@class,'search-result__info')]/a[@data-control-name='search_srp_result']")).get(num).getAttribute("href");
    }

    public String getRecruitmentName(int num) {
        return driver.findElements(By.xpath("//h3[@class='name']/a")).get(num).getAttribute("href");
    }

    private static void waiting(WebElement elem) {
        int counter = 0;
        try {
            Thread.sleep(20);
            while ((!elem.isDisplayed()) && (counter < 10)) {

                Thread.sleep(20);
                counter++;
            }

        } catch (Exception ex) {

        }
    }

}
