package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

public class LoginTest extends RailwayTest {

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();

    @Test
    public void tc001_verifyUsersCanLoginInSuccessfullyPOM()  {
        homePage.open();
        homePage.navigateToLoginPage();

        String username = "test@vnuk.vn";
        loginPage.login(username, "123456789");

        Assert.assertEquals(homePage.getGreetingText(), "Welcome " + username);
    }
}
