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
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(
                    org.openqa.selenium.By.cssSelector("div.account strong"), 
                    "Welcome " + username
                ));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Đăng nhập thất bại cho " + username + ". Đang tiến hành đăng ký tự động...");
            String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
            String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
            WebDriverUtils.get().get(baseUrl + "/Account/Register.cshtml");
            
            RegisterPage registerPage = new RegisterPage();
            registerPage.register(username, password, password, "123456789");
            
            WebDriverUtils.get().get(baseUrl + "/Account/Login.cshtml");
            new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(usernameTxtBy));
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
            
            new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(
                    org.openqa.selenium.By.cssSelector("div.account strong"), 
                    "Welcome " + username
                ));
        }
    }

    public void enterUsername(String username) {
        WebDriverUtils.get().findElement(usernameTxtBy).sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
    }

    public void clickLoginButton() {
        org.openqa.selenium.WebElement element = WebDriverUtils.get().findElement(loginBtnBy);
        try {
            element.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) WebDriverUtils.get()).executeScript("arguments[0].click();", element);
        }
    }

}
