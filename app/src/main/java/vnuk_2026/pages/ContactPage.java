package vnuk_2026.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import vnuk_2026.utils.WebDriverUtils;

public class ContactPage {

    private final By emailLinkBy = By.cssSelector("a[href='mailto:thanh.viet.le@logigear.com']");

    public boolean isContactDetailsDisplayed() {
        try {
            WebDriverUtils.removeAds();
            WebElement emailLink = new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(emailLinkBy));
            return emailLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailLinkHref() {
        WebDriverUtils.removeAds();
        WebElement emailLink = new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(emailLinkBy));
        return emailLink.getAttribute("href");
    }
}
