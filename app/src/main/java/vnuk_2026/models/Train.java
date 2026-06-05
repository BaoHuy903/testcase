package vnuk_2026.models;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Train {
    private String from;
    private String to;
    private LocalTime departTime;
    private LocalTime arriveTime;
}
