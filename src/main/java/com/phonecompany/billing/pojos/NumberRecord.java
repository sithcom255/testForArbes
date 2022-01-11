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
    private final static double LOW_RATE = 0.5;
    private final double MORE_MINUTES_RATE = 0.2;
    private final int HIGH_RATE = 1;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Duration timeInCall = Duration.ofSeconds(0);

    public void addCall(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        timeInCall = timeInCall.plusSeconds(Duration.between(start, end).toSeconds());

        if(minutes > 5) {
            addToPrice(minutes - 5, MORE_MINUTES_RATE);
            minutes = 5;
        }

        //this should be in constants or enum elsewhere
        LocalTime highPriceEnd = LocalTime.of(16, 0, 0);
        LocalTime highPriceStart = LocalTime.of(8, 0, 0);

        if(start.toLocalTime().isBefore(highPriceStart.minusMinutes(5))) {
            addToPrice(minutes, LOW_RATE);
        } else if (start.toLocalTime().isBefore(highPriceStart)) {
            callculatePriceOnEdgeOfRates(start, minutes, highPriceStart, LOW_RATE, HIGH_RATE);
        } else if (start.toLocalTime().isBefore(highPriceEnd.minusMinutes(5))) {
            addToPrice(minutes, HIGH_RATE);
        } else if (start.toLocalTime().isBefore(highPriceEnd)) {
            callculatePriceOnEdgeOfRates(start, minutes, highPriceEnd, HIGH_RATE, LOW_RATE);
        } else {
            addToPrice(minutes, LOW_RATE);
        }
    }

    private void callculatePriceOnEdgeOfRates(LocalDateTime start, long minutes, LocalTime edgeOfRates, double beforeRate, double afterRate) {
        long minutesToLow = (long) Math.ceil(Duration.between(start.toLocalTime(), edgeOfRates).toSeconds() / 60.0);
        if(minutes < minutesToLow) {
            addToPrice(minutesToLow, beforeRate);
        } else {
            addToPrice(minutesToLow, beforeRate);
            minutes -= minutesToLow;
            addToPrice(minutes, afterRate);
        }
    }

    private void addToPrice(long minutes, double lowRate) {
        price = price.add(BigDecimal.valueOf((minutes) * lowRate));
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
