package com.phonecompany.billing.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;

@RequiredArgsConstructor
@Getter
@Setter
public class NumberRecord {
    private final int number;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Duration timeInCall = Duration.ofSeconds(0);

    public void addCall(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        timeInCall = timeInCall.plusSeconds(Duration.between(start, end).toSeconds());

        if(minutes > 5) {
            price = price.add(BigDecimal.valueOf((minutes - 5) * 0.2));
            minutes = 5;
        }

        LocalTime highPriceEnd = LocalTime.of(16, 0, 0);
        LocalTime highPriceStart = LocalTime.of(8, 0, 0);

        price = price.add(BigDecimal.valueOf((minutes) * 0.5));
    }


}
