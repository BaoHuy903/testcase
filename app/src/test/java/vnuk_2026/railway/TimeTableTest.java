package vnuk_2026.railway;

import java.time.LocalTime;

import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.models.Train;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.TimeTablePage;

public class TimeTableTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    TimeTablePage timeTablePage = new TimeTablePage();

    @Test
    public void verifyArrivalTimeForTrainFromDaNang() {
        homePage.open();

        homePage.navigateToTimeTablePage();

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Sài Gòn"),
            LocalTime.of(7, 20)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Nha Trang"),
            LocalTime.of(14, 0)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Huế"),
            LocalTime.of(18, 0)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Quảng Ngãi"),
            LocalTime.of(6, 30)
        );
    }

    @Test
    public void verifyTrainInfo() {
        homePage.open();

        homePage.navigateToTimeTablePage();

        Train secondTrain = timeTablePage.getTrainByIndex(2);

        Train expectedTrain = Train.builder()
        .from("Sài Gòn")
        .to("Nha Trang")
        .departTime(LocalTime.of(6, 0))
        .arriveTime(LocalTime.of(14, 0))
        .build();

        Assert.assertEquals(secondTrain, expectedTrain);
    }

}
