# SauceDemo Test Automation Suite

End-to-end test automation suite for [saucedemo.com](https://www.saucedemo.com) built with Java, Selenium WebDriver, and JUnit 5. Covers critical user journeys across login, inventory, cart, and checkout flows with CI/CD integration via GitHub Actions.

## Test Coverage

| Suite | Scenarios Covered |
|---|---|
| LoginTest | Valid login, invalid credentials, locked out user, empty field validation |
| InventoryTest | Product count, item details, sort by price, add to cart badge |
| CartTest | Item in cart, remove item, continue shopping |
| CheckoutTest | Empty form validation, valid info, complete order confirmation |

## Risk Analysis

**High risk areas tested first:**
- Authentication flows — invalid access is the most critical failure mode
- Cart state — incorrect item tracking directly affects user trust
- Checkout completion — order confirmation is the core business transaction

**Edge cases covered:**
- Locked out user account handling
- Empty form submissions at login and checkout
- Cart badge count accuracy after add/remove

## Tech Stack

- Java 17
- Selenium WebDriver 4.18
- JUnit 5
- WebDriverManager (automatic ChromeDriver management)
- Maven
- GitHub Actions (CI/CD)

## Running Locally

```bash
mvn test
```

## Running a Single Test Class

```bash
mvn test -Dtest=LoginTest
```

## CI/CD

Tests run automatically on every push and pull request to `main` via GitHub Actions. Results are uploaded as artifacts for review.