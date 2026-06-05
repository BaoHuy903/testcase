package vnuk_2026.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookTicketForm {
    private LocalDate deparDate;
    private String departFrom;
    private String arriveTo;
    private SeatType seatType;
    private int amount;

    public enum SeatType {
        HARD_SEAT("Hard seat"),
        SOFT_SEAT("Soft seat"),
        SOFT_SEAT_AC("Soft seat with air conditioner"),
        HARD_BED("Hard bed"),
        SOFT_BED("Soft bed"),
        SOFT_BED_AC("Soft bed with air conditioner"),
        ;

        private SeatType(String text) {
            this.text = text;
        }

        private final String text;
        public String getText() {
            return text;
        }
    }
}
