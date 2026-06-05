package vnuk_2026.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import vnuk_2026.models.BookTicketForm;
import vnuk_2026.utils.WebDriverUtils;

public class BookTicketPage {

    private final By departDateDrdBy = By.name("Date");
    private final By departFromDrdBy = By.name("DepartStation");
    private final By arriveToDrdBy = By.name("ArriveStation");
    private final By seatTypeDrdBy = By.name("SeatType");
    private final By ticketAmountDrdBy = By.name("TicketAmount");
    private final By bookTicketBtnBy = By.cssSelector("input[type=submit][value='Book ticket']");

    public void bookTicket(BookTicketForm data) {
        selectDepartDate(data.getDeparDate());
        selectDepart(data.getDepartFrom());
        selectArrive(data.getArriveTo());
        selectSeatType(data.getSeatType());
        selectTicketAmount(data.getAmount());
        clickBookTicketButton();
    }
    
    public void selectDepartDate(LocalDate date) {
        Select select = new Select(WebDriverUtils.get().findElement(departDateDrdBy));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        select.selectByVisibleText(date.format(formatter));
    }

    public void selectTicketAmount(int amount) {
        Select select = new Select(WebDriverUtils.get().findElement(ticketAmountDrdBy));
        select.selectByVisibleText(String.valueOf(amount));
    }

    public void selectDepart(String from) {
        Select select = new Select(WebDriverUtils.get().findElement(departFromDrdBy));
        select.selectByVisibleText(from);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    public void selectArrive(String to) {
        Select select = new Select(WebDriverUtils.get().findElement(arriveToDrdBy));
        select.selectByVisibleText(to);
    }

    public void selectSeatType(BookTicketForm.SeatType seatType) {
        Select select = new Select(WebDriverUtils.get().findElement(seatTypeDrdBy));
        select.selectByVisibleText(seatType.getText());
    }

    public void clickBookTicketButton() {
        // Scroll to end
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebDriverUtils.get().findElement(bookTicketBtnBy).click();
    }

}
