package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "Дмитрий, +79122518775",
            "Дмитрий Тарасов, +12345678910",
            "Дмитрий-Тарасов, +00000000000",
            "Дмитрий-Тарасов Алексеевич, +99999999999"
    })
    void positiveOrder(String name, String phone) {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(phone);
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

}
