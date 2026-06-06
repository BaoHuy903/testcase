package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.FAQPage;
import vnuk_2026.pages.HomePage;

public class FAQTest extends RailwayTest {

    private final HomePage homePage = new HomePage();
    private final FAQPage faqPage = new FAQPage();

    @Test
    public void verifyFAQQuestionsScroll() {
        homePage.open();
        homePage.navigateToFAQPage();

        for (int i = 1; i <= 8; i++) {
            String href = faqPage.getQuestionHref(i);
            Assert.assertTrue(href.endsWith("#" + i), "Đường dẫn câu hỏi " + i + " phải kết thúc bằng #" + i);

            faqPage.clickQuestion(i);
            Assert.assertTrue(faqPage.isAnswerTargetDisplayed(i),
                    "Mục tiêu câu trả lời " + i + " phải được hiển thị trên trang.");
        }
    }

    @Test
    public void verifyFAQEmbeddedLinks() {
        homePage.open();
        homePage.navigateToFAQPage();

        faqPage.clickCreateAccountLink();
        Assert.assertTrue(
                vnuk_2026.utils.WebDriverUtils.get().getCurrentUrl().toLowerCase().contains("register"),
                "Phải chuyển hướng đến trang đăng ký/tạo tài khoản");
        homePage.navigateToFAQPage();

        faqPage.clickBookTicketLink();

        String currentUrl = vnuk_2026.utils.WebDriverUtils.get().getCurrentUrl().toLowerCase();
        Assert.assertTrue(
                currentUrl.contains("login") || currentUrl.contains("bookticket"),
                "chuyển hướng đến trang đặt vé");
    }

    @Test
    public void verifyFAQTabHighlighted() {
        homePage.open();
        homePage.navigateToFAQPage();

        Assert.assertTrue(homePage.isFAQTabSelected(), "Tab FAQ phải được làm nổi bật (được chọn) khi đang hoạt động.");
    }
}
