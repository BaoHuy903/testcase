package vnuk_2026.railway;

import org.testng.annotations.AfterClass;

import vnuk_2026.utils.WebDriverUtils;

public class RailwayTest {
    

    @AfterClass
    public void quitDriver() {
        WebDriverUtils.quit();
    }
}
