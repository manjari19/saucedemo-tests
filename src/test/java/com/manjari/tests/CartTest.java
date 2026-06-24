package com.manjari.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart Test Suite")
public class CartTest extends BaseTest {

    @BeforeEach
    public void loginAndAddItem() {
        driver.findElement(By.id("user-name")).sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        driver.findElement(By.className("btn_inventory")).click();
    }

    @Test
    @DisplayName("Cart should contain added item")
    public void testCartContainsItem() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(1, cartItems.size(), "Cart should contain exactly one item");
    }

    @Test
    @DisplayName("Removing item from cart should empty the cart")
    public void testRemoveItemFromCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        driver.findElement(By.className("cart_button")).click();
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertTrue(cartItems.isEmpty(), "Cart should be empty after removing item");
    }

    @Test
    @DisplayName("Continue shopping should return to inventory")
    public void testContinueShopping() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        driver.findElement(By.id("continue-shopping")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"),
            "Should return to inventory page");
    }
}