package vnuk_2026.railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

// @Test
public class LoginTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @Test
    public void tc001_verifyUsersCanLoginInSuccessfullyPOM()  {
        homePage.open();

        homePage.navigateToLoginPage();

        loginPage.login("example@udn.vn", "123456789");

        Assert.assertEquals(homePage.getGreetingText(), "Welcome example@udn.vn");
    }
}
