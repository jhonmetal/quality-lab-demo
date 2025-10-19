package com.example.qualitydemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumRegistrationTest {
    @Test
    public void testRegisterPageLoads() throws Exception {
    // Wait for the Spring Boot app to be reachable before starting ChromeDriver
    // Increased timeout because CI runners can be slower; allow up to 90 seconds
    waitForServerUp("http://localhost:8080/register", Duration.ofSeconds(90));

        // Create a unique user data dir for this test run to avoid collisions in CI
        Path userDataDir = Files.createTempDirectory("selenium-user-data-");

        ChromeOptions options = new ChromeOptions();
        // Use the newer headless mode when available; fall back is ok on older browsers
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        // Allow remote origins (fixes some CI Chrome/Chromedriver CORS issues)
        options.addArguments("--remote-allow-origins=*");
        // Provide an explicit, unique user data dir to avoid "already in use" errors
        options.addArguments("--user-data-dir=" + userDataDir.toAbsolutePath().toString());

        WebDriver driver = new ChromeDriver(options);
        try {
            // Use an explicit wait to avoid timing races while the Spring Boot app starts
            driver.get("http://localhost:8080/register");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            String page = driver.getPageSource();
            assertTrue(page.contains("Register"), "Register page should contain 'Register' text");
        } finally {
            try {
                driver.quit();
            } finally {
                // cleanup the temp profile directory
                try {
                    Files.walk(userDataDir)
                        .sorted((a, b) -> b.compareTo(a)) // delete children first
                        .forEach(p -> {
                            try { Files.deleteIfExists(p); } catch (Exception ignore) {}
                        });
                } catch (Exception ignore) {}
            }
        }
    }

    private static void waitForServerUp(String urlString, Duration timeout) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < deadline) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(2000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                // consider server up when we get any 2xx or 3xx response
                if (code >= 200 && code < 400) {
                    return;
                }
            } catch (Exception ignored) {
                // ignore and retry
            }
            Thread.sleep(1000);
        }
        throw new RuntimeException("Timed out waiting for server to be reachable at " + urlString);
    }
}
