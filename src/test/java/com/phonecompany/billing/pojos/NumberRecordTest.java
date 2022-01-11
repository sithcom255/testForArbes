package com.phonecompany.billing.pojos;

import org.assertj.core.api.SoftAssertions;
import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class NumberRecordTest {

    private final SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void calculateCallPriceValid() {
        String startS = "13-01-2020 18:10:15";
        String endS = "13-01-2020 18:12:57";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(startS, formatter);
        LocalDateTime end = LocalDateTime.parse(endS, formatter);
        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(1), Duration.ofSeconds(162));
    }

    private void checkCallRecord(NumberRecord record, BigDecimal price, Duration duration) {
        softAssertions.assertThat(record.getPrice()).isEqualTo(price);
        softAssertions.assertThat(record.getTimeInCall()).isEqualTo(duration);
        softAssertions.assertAll();
    }
}