package com.manjari.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Login Test Suite")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Valid credentials should redirect to inventory page")
    public void testValidLogin() {
        driver.findElement(By.id("user-name")).sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"),
            "Should redirect to inventory page after valid login");
    }

    @Test
    @DisplayName("Invalid credentials should show error message")
    public void testInvalidLogin() {
        driver.findElement(By.id("user-name")).sendKeys("wrong_user");
        driver.findElement(By.id("password")).sendKeys("wrong_pass");
        driver.findElement(By.id("login-button")).click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='error']")));
        assertTrue(error.isDisplayed(), "Error message should be visible");
        assertTrue(error.getText().contains("Username and password do not match"),
            "Error should mention credential mismatch");
    }

    @Test
    @DisplayName("Locked out user should see specific error message")
    public void testLockedOutUser() {
        driver.findElement(By.id("user-name")).sendKeys(LOCKED_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='error']")));
        assertTrue(error.getText().contains("locked out"),
            "Locked user should see locked out message");
    }

    @Test
    @DisplayName("Empty fields should show validation error")
    public void testEmptyFieldsLogin() {
        driver.findElement(By.id("login-button")).click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='error']")));
        assertTrue(error.isDisplayed(), "Validation error should appear for empty fields");
    }
}