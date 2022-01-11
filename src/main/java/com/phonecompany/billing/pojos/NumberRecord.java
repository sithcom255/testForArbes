package com.phonecompany.billing.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

@RequiredArgsConstructor
@Getter
@Setter
public class NumberRecord {
    private final int number;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Duration timeInCall = Duration.ofSeconds(0);

    public BigDecimal addCall(LocalDateTime start, LocalDateTime end) {
        long l = Duration.between(start, end).toMinutes();
        timeInCall = timeInCall.plusSeconds(Duration.between(start, end).toSeconds());

        return null;
    }


}
