package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    public static void main(String arg[]){
        WebDriver browser;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Utente\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("http://www.calcoloradicequadrata.com");
        browser.findElement(By.xpath("//*[@id=\"raiz1\"]")).sendKeys("4");
        browser.findElement(By.xpath("//*[@id=\"divRaiz1\"]/table/tbody/tr/td[3]/table/tbody/tr[1]/td/div/input")).click();
        String n=browser.findElement(By.xpath("//*[@id=\"resultado1\"]")).getAttribute("value");
        System.out.println(n);
        browser.close();
    }

}
