package com.example.qualitydemo;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumRegistrationTest {
    @Test
    public void testRegisterPageLoads() {
        // Configure Chrome options for headless testing (works in CI and local)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");           // Run in headless mode
        options.addArguments("--no-sandbox");         // Disable sandbox (needed in Docker/CI)
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        options.addArguments("--disable-gpu");        // Disable GPU acceleration
        options.addArguments("--start-maximized");    // Start with maximized window
        
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("http://localhost:8080/register");
            assertTrue(driver.getPageSource().contains("Register"), 
                "Register page should contain 'Register' text");
        } finally {
            driver.quit();
        }
    }
}
