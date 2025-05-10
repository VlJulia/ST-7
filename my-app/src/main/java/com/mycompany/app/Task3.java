package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.io.FileWriter;

public class Task3 {
    public static void Task3Run() {
        System.setProperty("webdriver.chrome.driver", "D:/DriverForLab7/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56.3287&longitude=44.002&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";

            driver.get(url);
            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            if (!obj.containsKey("hourly")) {
                throw new Exception("Error");
            }

            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");
            String date = "Прогноз погоды на " + times.get(0).toString().substring(0,10) + " - Нижний Новгород:\n";
            new java.io.File("result").mkdirs();
            System.out.println(date);
            try (FileWriter writer = new FileWriter("result/forecast.txt")) {
                writer.write(date);
                writer.write("Время\tt\tОсадки\n");

                for (int i = 0; i < times.size(); i++) {
                    String time = ((String) times.get(i)).substring(11);
                    String temp = temps.get(i).toString();
                    String rain = rains.get(i).toString();

                    System.out.printf("%s\t%s°C\t%s\n",
                            time, temp, rain);
                    writer.write(String.format("%s\t%s°C\t%s\n",
                             time, temp, rain));
                }
                System.out.println("SAVED");
            }
        } catch (Exception e) {
            System.out.println( e.getMessage());
        } finally {
            driver.quit();
        }
    }
}