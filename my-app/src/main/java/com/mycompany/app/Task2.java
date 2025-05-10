package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task2 {
    public static void Task2Run() {
        System.setProperty("webdriver.chrome.driver", "D:/DriverForLab7/chromedriver-win64/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();



        try {
            webDriver.get("https://api.ipify.org/?format=json");

            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String json_str = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);

            String ip = (String) obj.get("ip");


        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            webDriver.quit();
        }
    }
}