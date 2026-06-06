package vnuk_2026.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import vnuk_2026.utils.WebDriverUtils;

public class FAQPage {

    public String getQuestionHref(int index) {
        WebElement questionLink = WebDriverUtils.get().findElement(By.cssSelector("a[href='#" + index + "']"));
        return questionLink.getAttribute("href");
    }

    public void clickQuestion(int index) {
        WebDriverUtils.get().findElement(By.cssSelector("a[href='#" + index + "']")).click();
    }

    public boolean isAnswerTargetDisplayed(int index) {
        try {
            return WebDriverUtils.get().findElement(By.id(String.valueOf(index))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCreateAccountLink() {
        WebDriverUtils.removeAds();
        org.openqa.selenium.WebElement element = WebDriverUtils.get().findElement(By.xpath("//*[@id='1']//a[contains(@href, 'Register')]"));
        try {
            element.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) WebDriverUtils.get()).executeScript("arguments[0].click();", element);
        }
    }

    public void clickBookTicketLink() {
        WebDriverUtils.removeAds();
        org.openqa.selenium.WebElement element = WebDriverUtils.get().findElement(By.xpath("//*[@id='1']//a[contains(@href, 'BookTicket')]"));
        try {
            element.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) WebDriverUtils.get()).executeScript("arguments[0].click();", element);
        }
    }
}
