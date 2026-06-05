package vnuk_2026.pages;

import org.openqa.selenium.By;

import vnuk_2026.utils.WebDriverUtils;

public class HomePage {

    private final By loginMenuBy = By.linkText("Login"); // locator
    private final By bookTicketMenuBy = By.linkText("Book ticket"); // locator
    private final By greetingLblBy = By.cssSelector("div.account strong");

    /**
     * Navigate to Railways home page (/)
     */
    public void open() {
        WebDriverUtils.get().get("http://railwayb2.somee.com");
    }

    /**
     * Navigate to Login page from nav bar
     * 
     */
    public void navigateToLoginPage() {
        WebDriverUtils.get().findElement(loginMenuBy).click();
    }

    public void navigateToBookTicketPage() {
        WebDriverUtils.get().findElement(bookTicketMenuBy).click();
    }

    public void navigateToTimeTablePage() {
        WebDriverUtils.get().findElement(By.linkText("Timetable")).click();
    }

    public String getGreetingText() {
        return WebDriverUtils.get().findElement(greetingLblBy).getText();
    }

    
}
