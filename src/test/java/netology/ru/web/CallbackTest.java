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
    //running the driver
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
        //creating driver object
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        //running the form at URL
        driver.get("http://localhost:9999");
        //entering first name
        driver.findElement(By.cssSelector("[data-test-id =name]")).sendKeys("Василий");
        //entering phone
        driver.findElement(By.cssSelector("[data-test-id =phone]")).sendKeys("+77777777777");
        //clicking checkbox
        driver.findElement(By.cssSelector("[data-test-id =agreement]")).click();
        //clicking sign up
        driver.findElement(By.tagName("button")).click();
        //getting message after the form is submitted
        String actualText  = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        //assering to expectations
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());
    }
}

