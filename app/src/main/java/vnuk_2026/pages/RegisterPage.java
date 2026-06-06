package vnuk_2026.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import vnuk_2026.utils.WebDriverUtils;

public class RegisterPage {

    private final By emailTxtBy = By.id("email");
    private final By passwordTxtBy = By.id("password");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By pidTxtBy = By.id("pid");
    private final By registerBtnBy = By.cssSelector("input[value='Register'], input[title='Register']");

    public void register(String email, String password, String confirmPassword, String pid) {
        WebDriverUtils.removeAds();
        new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(emailTxtBy));
            
        WebDriverUtils.get().findElement(emailTxtBy).sendKeys(email);
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
        WebDriverUtils.get().findElement(confirmPasswordTxtBy).sendKeys(confirmPassword);
        WebDriverUtils.get().findElement(pidTxtBy).sendKeys(pid);
        
        WebElement element = WebDriverUtils.get().findElement(registerBtnBy);
        try {
            element.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) WebDriverUtils.get()).executeScript("arguments[0].click();", element);
        }
        
        // Chờ và kiểm tra nếu có liên kết kích hoạt/xác nhận trên trang thì click vào
        try {
            Thread.sleep(1000);
            var links = WebDriverUtils.get().findElements(By.tagName("a"));
            for (var link : links) {
                String href = link.getAttribute("href");
                if (href != null && href.toLowerCase().contains("confirm")) {
                    link.click();
                    break;
                }
            }
        } catch (Exception ignored) {}
    }
}
