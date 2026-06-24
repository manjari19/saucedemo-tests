package com.manjari.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Checkout Test Suite")
public class CheckoutTest extends BaseTest {

    private void jsClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private void jsType(String id, String text) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        // Clear field first, type character by character to trigger input events
        el.clear();
        el.sendKeys(text);
    }

    @BeforeEach
    public void loginAddItemAndGoToCheckout() {
        driver.findElement(By.id("user-name")).sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        jsClick(By.id("login-button"));
        wait.until(ExpectedConditions.urlContains("inventory"));
        jsClick(By.className("btn_inventory"));
        jsClick(By.className("shopping_cart_link"));
        wait.until(ExpectedConditions.urlContains("cart"));
        jsClick(By.id("checkout"));
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    @Test
    @DisplayName("Empty checkout form should show validation error")
    public void testEmptyCheckoutForm() {
        jsClick(By.id("continue"));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='error']")));
        assertTrue(error.isDisplayed(),
            "Validation error should appear for empty checkout form");
    }

    @Test
    @DisplayName("Valid checkout info should proceed to order summary")
    public void testValidCheckoutInfo() {
        jsType("first-name", "Manjari");
        jsType("last-name", "Prasad");
        jsType("postal-code", "V3E 0A1");
        jsClick(By.id("continue"));
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
            "Should proceed to order summary page");
    }

    @Test
    @DisplayName("Complete order should show confirmation message")
    public void testCompleteOrder() {
        jsType("first-name", "Manjari");
        jsType("last-name", "Prasad");
        jsType("postal-code", "V3E 0A1");
        jsClick(By.id("continue"));
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        jsClick(By.id("finish"));
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.className("complete-header")));
        assertTrue(header.isDisplayed(),
            "Order confirmation header should be visible");
    }
}