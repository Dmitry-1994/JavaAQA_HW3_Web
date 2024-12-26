package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class OrderPositiveTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void startDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void endDriver() {
        driver.quit();
        driver = null;
    }

    @Test
    void positiveOrder() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

}
