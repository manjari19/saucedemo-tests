package com.manjari.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Inventory Page Test Suite")
public class InventoryTest extends BaseTest {

    @BeforeEach
    public void login() {
        driver.findElement(By.id("user-name")).sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    @Test
    @DisplayName("Inventory page should display 6 products")
    public void testInventoryItemCount() {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        assertEquals(6, items.size(), "Inventory should display exactly 6 products");
    }

    @Test
    @DisplayName("Each product should have name, description, and price")
    public void testInventoryItemDetails() {
        List<WebElement> names = driver.findElements(By.className("inventory_item_name"));
        List<WebElement> descs = driver.findElements(By.className("inventory_item_desc"));
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        assertFalse(names.isEmpty(), "Products should have names");
        assertFalse(descs.isEmpty(), "Products should have descriptions");
        assertFalse(prices.isEmpty(), "Products should have prices");
        assertEquals(names.size(), prices.size(), "Each product should have a price");
    }

    @Test
    @DisplayName("Sort by price low to high should reorder products correctly")
    public void testSortByPriceLowToHigh() {
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("lohi");
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        double last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        assertTrue(first <= last, "First price should be less than or equal to last price");
    }

    @Test
    @DisplayName("Add to cart button should update cart badge")
    public void testAddToCartUpdatesBadge() {
        driver.findElement(By.className("btn_inventory")).click();
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.className("shopping_cart_badge")));
        assertEquals("1", badge.getText(), "Cart badge should show 1 after adding one item");
    }
}