package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.ContactPage;
import vnuk_2026.pages.HomePage;

public class ContactTest extends RailwayTest {

    private final HomePage homePage = new HomePage();
    private final ContactPage contactPage = new ContactPage();

    @Test
    public void verifyContactDetailsDisplayed() {
        homePage.open();
        homePage.navigateToContactPage();

        Assert.assertTrue(contactPage.isContactDetailsDisplayed(), "Thông tin liên hệ phải được hiển thị.");
    }

    @Test
    public void verifyEmailLinkWorks() {
        homePage.open();
        homePage.navigateToContactPage();

        String emailHref = contactPage.getEmailLinkHref();
        Assert.assertEquals(emailHref, "mailto:thanh.viet.le@logigear.com", "Đường dẫn liên kết email khớp.");
    }

    @Test
    public void verifyContactTabHighlighted() {
        homePage.open();
        homePage.navigateToContactPage();

        Assert.assertTrue(homePage.isContactTabSelected(), "Tab Liên hệ phải được làm nổi bật khi đang hoạt động.");
    }
}
