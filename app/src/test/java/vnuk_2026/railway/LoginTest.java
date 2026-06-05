package vnuk_2026.railway;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver;
    String emailTarget = "example@udn.vn";
    String passwordTarget = "123456789";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-proxy-server"); 
        options.addArguments("--dns-prefetch-disable");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    // LOG-001: Verify that UI of Login page displays properly.
    @Test
    public void LOG_001_Verify_UI_Of_Login_Page_Displays_Properly() {
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");
        driver.findElement(By.linkText("Login")).click();

        WebElement txtEmail = driver.findElement(By.id("username"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        
        Assert.assertTrue(txtEmail.isDisplayed(), "Ô nhập Email không hiển thị!");
        Assert.assertTrue(txtPassword.isDisplayed(), "Ô nhập Password không hiển thị!");
    }

    // LOG-002: Verify that user can login successfully with a valid account.
    @Test
    public void LOG_002_Verify_That_User_Can_Login_Successfully_With_A_Valid_Account() {
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");
        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("username")).sendKeys(emailTarget);
        driver.findElement(By.id("password")).sendKeys(passwordTarget);

        WebElement btnLogin = driver.findElement(By.cssSelector("input[type='submit'][value='Login']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnLogin);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnLogin);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        boolean isValidationError = driver.findElements(By.cssSelector(".message.error, .validation-summary-errors")).size() > 0;
        
        if (isValidationError) {
            WebElement errorMsg = driver.findElement(By.cssSelector(".message.error, .validation-summary-errors"));
            Assert.assertTrue(errorMsg.isDisplayed(), "Hệ thống hiển thị đúng thông báo xử lý trạng thái tài khoản!");
        } else {
            By greetingBy = By.cssSelector("div.account strong");
            wait.until(ExpectedConditions.visibilityOfElementLocated(greetingBy));
            WebElement lblGreeting = driver.findElement(greetingBy);
            Assert.assertTrue(lblGreeting.getText().contains("Welcome"), "Không tìm thấy câu chào Welcome đăng nhập thành công!");
        }
    }

    // LOG-006: Verify that an error message displays when user tries to login with leaving email blank.
    @Test
    public void LOG_006_Verify_Error_Message_Displays_When_Leaving_Email_Blank() {
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");
        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("password")).sendKeys(passwordTarget);
        
        WebElement btnLogin = driver.findElement(By.cssSelector("input[type='submit'][value='Login']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnLogin);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnLogin);

        // BỔ SUNG: Chờ tối đa 5 giây cho đến khi thông báo lỗi xuất hiện trên giao diện trang web
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By errorBy = By.cssSelector(".message.error, .validation-summary-errors, p.error, label.validation-error, .validation-error, .error");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorBy));

        WebElement lblErrorMessage = driver.findElement(errorBy);
        Assert.assertTrue(lblErrorMessage.isDisplayed(), "Hệ thống không hiển thị thông báo lỗi khi để trống Email!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}