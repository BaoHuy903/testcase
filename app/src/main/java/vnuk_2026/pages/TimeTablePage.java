package vnuk_2026.pages;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import vnuk_2026.models.Train;
import vnuk_2026.utils.WebDriverUtils;

public class TimeTablePage {

    private final By tableHeadersBy = By.xpath("//tr/th");
    private final By tableRowBy = By.xpath("//tbody/tr");
    // css: tbody > tr
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    
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


        return LocalTime.parse(departureTimeCell.getText(), formatter);
    }

    public Train getTrainByIndex(int rowIndex) { // rowIndex starts at 1
        var headers = getTableHeaders();

        WebElement row = WebDriverUtils.get()
        .findElements(tableRowBy)
        .get(rowIndex - 1); // 0-based index

        List<WebElement> cells = row.findElements(By.tagName("td"));

        return Train.builder()
            .from(cells.get(headers.indexOf("Depart Station")).getText())
            .to(cells.get(headers.indexOf("Arrive Station")).getText())
            .arriveTime(
                LocalTime.parse(cells.get(headers.indexOf("Arrive Time")).getText(), formatter)
            )
            .departTime(
                LocalTime.parse(cells.get(headers.indexOf("Depart Time")).getText(), formatter)
            )
            .build();
    }

    private List<String> getTableHeaders() {
        return WebDriverUtils.get()
        .findElements(tableHeadersBy)
        .stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
    }

}