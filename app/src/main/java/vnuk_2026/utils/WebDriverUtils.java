package vnuk_2026.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverUtils {
    
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static WebDriver get() {
        if (drivers.get() == null) {
            // nếu chưa có driver, khởi tạo ChromeDriver mặc định
            ChromeOptions options = new ChromeOptions();
            // Avoid using the guest argument which can create unstable windows in some environments
            // Allow remote origins to prevent ChromeDriver startup issues with newer Chrome versions
            options.addArguments("--remote-allow-origins=*");
            var driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1920, 1080));
            drivers.set(
                driver
            );
        }
        return drivers.get();
    }

    public static void removeAds() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) get();
            js.executeScript(
                "var links = document.getElementsByTagName('a');" +
                "for (var i = 0; i < links.length; i++) {" +
                "    if (links[i].href && links[i].href.indexOf('somee.com') !== -1) {" +
                "        var p = links[i].parentNode;" +
                "        while (p && p.tagName !== 'BODY' && p.tagName !== 'HTML') {" +
                "            var style = window.getComputedStyle(p);" +
                "            if (style.position === 'fixed' || style.position === 'absolute' || parseInt(style.zIndex) > 1000) {" +
                "                p.style.display = 'none';" +
                "                p.style.visibility = 'hidden';" +
                "                p.remove();" +
                "                break;" +
                "            }" +
                "            p = p.parentNode;" +
                "        }" +
                "    }" +
                "}"
            );
        } catch (Exception ignored) {}
    }

    public static void quit() {
        WebDriver driver = drivers.get();
        if (driver != null) {
            driver.quit();
            drivers.remove();
        }
    }
}
