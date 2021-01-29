package netology.ru.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.cssSelector;

public class CallbackTest {
    private WebDriver driver;

    //    System.setProperty("webdriver.chrome.driver", "/pathTo/chromedriver);
    @BeforeAll
    //running the driver
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", ".\\driver\\win\\chromedriver.exe");
    }

    @BeforeEach
        //creating driver object
    void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//        options.setHeadless(true);
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestHappyPath() {
        //running the form at URL
        driver.get("http://localhost:9999");
        //entering first name
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Василий Чапаев");
        //entering phone
        driver.findElement(cssSelector("[type='tel']")).sendKeys("+74956783423");
        //clicking checkbox
        driver.findElement(By.className("checkbox__box")).click();
        //clicking sign up
        driver.findElement(By.tagName("button")).click();
        //getting message after the form is submitted
        String actualText = driver.findElement(cssSelector("[data-test-id]")).getText();
        //assering to expectations
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());
    }

    @Test
    void shouldWarnIfNameFieldIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+74956783423");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", actualText.trim());
    }

    @Test
    void shouldWarnIfPhoneFieldIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Василий Чапаев");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=phone] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", actualText.trim());
    }

    @Test
    void shouldWarnIfAllFieldsAreEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text1 = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", text1.trim());
        String text2 = driver.findElement(cssSelector("[data-test-id=phone] [class=input__sub]")).getText();
        assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.", text2.trim());
    }

    @Test
    void shouldWarnIfNameIsInLatin() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Vasily");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+74956783423");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText.trim());
    }

    @Test
    void shouldWarnIfNameIsWithNumbers() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("123");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+74956783423");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText.trim());
    }

    @Test
    void shouldWarnIfPhoneIsNot11Numbers() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+123456789");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText.trim());
    }

    @Test
    void shouldWarnIfPhoneIsFilledWithLetters() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("Василий");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText.trim());
    }

    @Test
    void shouldWarnIfCheckboxIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678999");
        driver.findElement(cssSelector("button")).click();
        boolean actual = driver.findElement(cssSelector(".input_invalid .checkbox__box")).isDisplayed();
        assertTrue(actual);
    }
}

