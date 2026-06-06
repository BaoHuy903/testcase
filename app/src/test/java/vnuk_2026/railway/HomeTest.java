package vnuk_2026.railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeTest {

    WebDriver driver;

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

    // HOM-001: Verify that UI of Home page displays properly.
    @Test
    public void HOM_001_Verify_UI_Of_Home_Page_Displays_Properly() {
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");
        
        // Kiểm tra tiêu đề chính (Header h1) của trang chủ hiển thị đúng chữ
        WebElement welcomeHeader = driver.findElement(By.cssSelector("#content h1"));
        Assert.assertTrue(welcomeHeader.isDisplayed(), "Tiêu đề trang chủ không hiển thị!");
        Assert.assertEquals(welcomeHeader.getText(), "Welcome to Safe Railway", "Nội dung tiêu đề trang chủ hiển thị sai!");
    }

    // HOM-002: Verify that user can navigate to all required tabs when not logged in yet.
    @Test
    public void HOM_002_Verify_Navigation_Tabs_When_Not_Logged_In() {
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");

        // 1. Kiểm tra các Tab cơ bản bắt buộc phải hiển thị khi chưa đăng nhập (theo Excel)
        Assert.assertTrue(driver.findElement(By.linkText("Home")).isDisplayed(), "Tab Home bị ẩn!");
        Assert.assertTrue(driver.findElement(By.linkText("Timetable")).isDisplayed(), "Tab Timetable bị ẩn!");
        Assert.assertTrue(driver.findElement(By.linkText("Register")).isDisplayed(), "Tab Register bị ẩn!");
        Assert.assertTrue(driver.findElement(By.linkText("Login")).isDisplayed(), "Tab Login bị ẩn!");

        // 2. Kiểm tra điều kiện loại trừ: Các tab bảo mật (Restricted tabs) KHÔNG ĐƯỢC PHÉP xuất hiện
        int isMyTicketPresent = driver.findElements(By.linkText("My Ticket")).size();
        int isChangePasswordPresent = driver.findElements(By.linkText("Change Password")).size();

        Assert.assertEquals(isMyTicketPresent, 0, "Lỗi bảo mật: Tab 'My Ticket' hiển thị công khai dù chưa đăng nhập!");
        Assert.assertEquals(isChangePasswordPresent, 0, "Lỗi bảo mật: Tab 'Change Password' hiển thị công khai dù chưa đăng nhập!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}