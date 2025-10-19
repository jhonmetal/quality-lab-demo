package com.example.qualitydemo;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumRegistrationTest {
    @Test
    public void testRegisterPageLoads() {
        // Requires chromedriver available on PATH in CI or local machine
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://localhost:8080/register");
            assertTrue(driver.getPageSource().contains("Register"));
        } finally {
            driver.quit();
        }
    }
}
