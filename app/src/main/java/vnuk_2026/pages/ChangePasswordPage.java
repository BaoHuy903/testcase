package vnuk_2026.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import vnuk_2026.utils.WebDriverUtils;

public class ChangePasswordPage {

    // Khai báo các Locators dựa theo thuộc tính ID từ ảnh chụp màn hình HTML
    private final By currentPasswordTxtBy = By.id("currentPassword");
    private final By newPasswordTxtBy = By.id("newPassword");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By changePasswordBtnBy = By.cssSelector("input[type=submit][value='Change Password']");
    
    // Locators cho thông báo lỗi hoặc thành công (thông thường của hệ thống Railway)
    private final By successMessageLblBy = By.cssSelector("p.message.success");
    private final By errorMessageLblBy = By.cssSelector("p.message.error");

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        enterCurrentPassword(currentPassword);
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickChangePasswordButton();
    }

    public void enterCurrentPassword(String currentPassword) {
        var element = WebDriverUtils.get().findElement(currentPasswordTxtBy);
        element.clear();
        element.sendKeys(currentPassword);
    }

    public void enterNewPassword(String newPassword) {
        var element = WebDriverUtils.get().findElement(newPasswordTxtBy);
        element.clear();
        element.sendKeys(newPassword);
    }

    public void enterConfirmPassword(String confirmPassword) {
        var element = WebDriverUtils.get().findElement(confirmPasswordTxtBy);
        element.clear();
        element.sendKeys(confirmPassword);
    }

    public void clickChangePasswordButton() {
        // Cuộn chuột xuống cuối trang để đảm bảo nút submit không bị che khuất bởi banner quảng cáo phía dưới
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebDriverUtils.get().findElement(changePasswordBtnBy).click();
    }

    public String getSuccessMessage() {
        return WebDriverUtils.get().findElement(successMessageLblBy).getText();
    }

    public String getErrorMessage() {
        return WebDriverUtils.get().findElement(errorMessageLblBy).getText();
    }
}