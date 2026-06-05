package vnuk_2026.pages;

import org.openqa.selenium.By;

import vnuk_2026.utils.WebDriverUtils;

public class LoginPage {


    private final By usernameTxtBy = By.cssSelector("#username");
    private final By passwordTxtBy = By.id("password");
    private final By loginBtnBy = By.cssSelector("[title=Login]");

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void enterUsername(String username) {
        WebDriverUtils.get().findElement(usernameTxtBy).sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
    }

    public void clickLoginButton() {
        WebDriverUtils.get().findElement(loginBtnBy).click();
    }

}
