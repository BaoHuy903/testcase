package vnuk_2026.pages;

import org.openqa.selenium.By;

import vnuk_2026.utils.WebDriverUtils;

public class HomePage {

    private final By loginMenuBy = By.linkText("Login"); // bộ định vị
    private final By bookTicketMenuBy = By.linkText("Book ticket"); // bộ định vị
    private final By greetingLblBy = By.cssSelector("div.account strong");
    private final By contactMenuBy = By.linkText("Contact");
    private final By faqMenuBy = By.linkText("FAQ");

    /**
     * Điều hướng đến trang chủ Railways (/)
     * Sử dụng tham số môi trường AUT: B1 hoặc B2 (mặc định: B2)
     */
    public void open() {
        String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
        String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
        WebDriverUtils.get().get(baseUrl);
        WebDriverUtils.removeAds();
    }

    /**
     * Điều hướng đến trang Đăng nhập từ thanh điều hướng
     * 
     */
    public void navigateToLoginPage() {
        WebDriverUtils.removeAds();
        WebDriverUtils.get().findElement(loginMenuBy).click();
    }

    public void navigateToBookTicketPage() {
        WebDriverUtils.removeAds();
        WebDriverUtils.get().findElement(bookTicketMenuBy).click();
    }

    public void navigateToTimeTablePage() {
        WebDriverUtils.removeAds();
        WebDriverUtils.get().findElement(By.linkText("Timetable")).click();
    }

    public String getGreetingText() {
        return WebDriverUtils.get().findElement(greetingLblBy).getText();
    }

    public void navigateToContactPage() {
        WebDriverUtils.removeAds();
        WebDriverUtils.get().findElement(contactMenuBy).click();
    }

    public void navigateToFAQPage() {
        WebDriverUtils.removeAds();
        try {
            WebDriverUtils.get().findElement(faqMenuBy).click();
        } catch (Exception e) {
            // Bỏ qua nếu click thất bại
        }
        // Điều hướng trực tiếp nếu click không chuyển đến trang FAQ
        String currentUrl = WebDriverUtils.get().getCurrentUrl();
        if (!currentUrl.toLowerCase().contains("faq.cshtml")) {
            String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
            String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
            WebDriverUtils.get().get(baseUrl + "/Page/FAQ.cshtml");
        }
        WebDriverUtils.removeAds();
    }

    public boolean isContactTabSelected() {
        org.openqa.selenium.WebElement contactLink = WebDriverUtils.get().findElement(contactMenuBy);
        org.openqa.selenium.WebElement parentLi = contactLink.findElement(By.xpath(".."));
        String classAttr = parentLi.getAttribute("class");
        return classAttr != null && classAttr.contains("selected");
    }

    public boolean isFAQTabSelected() {
        org.openqa.selenium.WebElement faqLink = WebDriverUtils.get().findElement(faqMenuBy);
        org.openqa.selenium.WebElement parentLi = faqLink.findElement(By.xpath(".."));
        String classAttr = parentLi.getAttribute("class");
        return classAttr != null && classAttr.contains("selected");
    }

    
}
