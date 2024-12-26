package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderNegativeTest {
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
    void emptyFieldAll() {
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        String expected = "Поле обязательно для заполнения";
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyFieldName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        String expected = "Поле обязательно для заполнения";
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "Dima",
            "Дмитрий_Tarasov",
            "!Дмитрий",
            "Дмитрий007"
    })
    void invalidName(String name) {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        String expected = "Поле обязательно для заполнения";
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "+791225187755",
            "+7912251877",
            "+7",
            "+",
            "89122518775",
            "@79122518775",
            "Дмитрий"
    })
    void invalidPhone(String namber) {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(namber);
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = result.getText().trim();

        Assertions.assertTrue(result.isDisplayed());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyFieldAgree() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));

        Assertions.assertTrue(result.isDisplayed());
    }

}
