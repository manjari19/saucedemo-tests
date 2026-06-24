package com.manjari.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart Test Suite")
public class CartTest extends BaseTest {

    private void jsClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    @BeforeEach
    public void loginAndAddItem() {
        driver.findElement(By.id("user-name")).sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        jsClick(By.id("login-button"));
        wait.until(ExpectedConditions.urlContains("inventory"));
        jsClick(By.className("btn_inventory"));
        jsClick(By.className("shopping_cart_link"));
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    @Test
    @DisplayName("Cart should contain added item")
    public void testCartContainsItem() {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(1, cartItems.size(), "Cart should contain exactly one item");
    }

    @Test
    @DisplayName("Removing item from cart should empty the cart")
    public void testRemoveItemFromCart() {
        jsClick(By.className("cart_button"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("cart_item")));
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertTrue(cartItems.isEmpty(), "Cart should be empty after removing item");
    }

    @Test
    @DisplayName("Continue shopping should return to inventory")
    public void testContinueShopping() {
        jsClick(By.id("continue-shopping"));
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"),
            "Should return to inventory page");
    }
}