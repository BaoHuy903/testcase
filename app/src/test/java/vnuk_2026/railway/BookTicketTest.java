package vnuk_2026.railway;

import java.time.LocalDate;

import org.testng.annotations.Test;

import vnuk_2026.models.BookTicketForm;
import vnuk_2026.pages.BookTicketPage;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

public class BookTicketTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BookTicketPage bookTicketPage = new BookTicketPage();

    BookTicketForm data2 = BookTicketForm.builder()
            .deparDate(LocalDate.now().plusWeeks(1))
            .departFrom("Đà Nẵng")
            .arriveTo("Nha Trang")
            .seatType(BookTicketForm.SeatType.HARD_BED)
            .amount(5)
        .build();

    @Test
    public void bookticketFlow() {
        homePage.open();

        homePage.navigateToBookTicketPage();

        loginPage.login("example@udn.vn", "123456789");

        homePage.navigateToBookTicketPage();

        bookTicketPage.bookTicket(data2);
    }

}
