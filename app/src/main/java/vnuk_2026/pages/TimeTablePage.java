package vnuk_2026.pages;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import vnuk_2026.utils.WebDriverUtils;

public class TimeTablePage {

    private final By tableHeadersBy = By.xpath("//tr/th");
    
    public LocalTime getDepartureTime(
        String departStation,
        String arriveStation
    ) {
        var headers = getTableHeaders();
        int departStationColumnIndex = headers.indexOf("Depart Station") + 1;
        int arriveStationColumnIndex = headers.indexOf("Arrive Station") + 1;
        int departTimeColumnIndex = headers.indexOf("Depart Time") + 1;

        By xpath = By.xpath(String.format("//tr[td[%d][text()='%s'] and td[%d][text()='%s']]/td[%d]", departStationColumnIndex, departStation, arriveStationColumnIndex, arriveStation, departTimeColumnIndex));
        WebElement departureTimeCell = WebDriverUtils.get().findElement(xpath);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        return LocalTime.parse(departureTimeCell.getText(), formatter);
    }


    private List<String> getTableHeaders() {
        return WebDriverUtils.get()
        .findElements(tableHeadersBy)
        .stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
    }

}
