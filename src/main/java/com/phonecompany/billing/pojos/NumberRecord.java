package com.phonecompany.billing.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
public class NumberRecord {
    private final long number;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Duration timeInCall = Duration.ofSeconds(0);

    public void addCall(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        timeInCall = timeInCall.plusSeconds(Duration.between(start, end).toSeconds());

        if(minutes > 5) {
            price = price.add(BigDecimal.valueOf((minutes - 5) * 0.2));
            minutes = 5;
        }

        //this should be in constants or enum elsewhere
        LocalTime highPriceEnd = LocalTime.of(16, 0, 0);
        LocalTime highPriceStart = LocalTime.of(8, 0, 0);

        if(start.toLocalTime().isBefore(highPriceStart.minusMinutes(5))) {
            price = price.add(BigDecimal.valueOf((minutes) * 0.5));
        } else if (start.toLocalTime().isBefore(highPriceStart)) {
            long minutesToHigh = (long) Math.ceil(Duration.between(start.toLocalTime(), highPriceStart).toSeconds() / 60.0);
            if(minutes < minutesToHigh) {
                price = price.add(BigDecimal.valueOf((minutes) * 0.5));
            } else {
                price = price.add(BigDecimal.valueOf((minutesToHigh) * 0.5));
                minutes -= minutesToHigh;
                price = price.add(BigDecimal.valueOf(minutes));
            }
        } else if (start.toLocalTime().isBefore(highPriceEnd.minusMinutes(5))) {
            price = price.add(BigDecimal.valueOf(minutes));
        } else if (start.toLocalTime().isBefore(highPriceEnd)) {
            long minutesToLow = (long) Math.ceil(Duration.between(start.toLocalTime(), highPriceEnd).toSeconds() / 60.0);

            if(minutes < minutesToLow) {
                price = price.add(BigDecimal.valueOf(minutes));
            } else {
                price = price.add(BigDecimal.valueOf(minutesToLow));
                minutes -= minutesToLow;
                price = price.add(BigDecimal.valueOf((minutes) * 0.5));
            }
        } else {
            price = price.add(BigDecimal.valueOf((minutes) * 0.5));
        }
    }

    public boolean isGreater(NumberRecord other) {
        if(Objects.isNull(other)) {
            return true;
        }
        if(this.getPrice().compareTo(other.getPrice()) == 1){
            return true;
        } else return this.getPrice().compareTo(other.getPrice()) == 0 && this.getNumber() > other.getNumber();
    }


}
