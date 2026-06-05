package vnuk_2026.pages;

import org.openqa.selenium.By;

import vnuk_2026.utils.WebDriverUtils;

public class HomePage {

    private final By loginMenuBy = By.linkText("Login");
    private final By bookTicketMenuBy = By.linkText("Book ticket");
    private final By changePasswordMenuBy = By.linkText("Change password");
    private final By greetingLblBy = By.cssSelector("div.account strong");

    /**
     * Navigate to Railways home page (/)
     * Uses AUT environment parameter: B1 or B2 (default: B2)
     */
    public void open() {
        String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
        String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
        WebDriverUtils.get().get(baseUrl);
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

    public void navigateToChangePasswordPage() {
        WebDriverUtils.get().findElement(changePasswordMenuBy).click();
    }
    
    public String getGreetingText() {
        return WebDriverUtils.get().findElement(greetingLblBy).getText();
    }

    
}
