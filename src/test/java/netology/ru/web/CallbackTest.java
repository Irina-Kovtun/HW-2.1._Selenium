package netology.ru.web;

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

public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //    @Test
//    void shouldTestSomething() {
//        driver.get("http://localhost:9999");
//    }
    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id =name]")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id =phone]")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector("[data-test-id =agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText  = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());
    }
}

