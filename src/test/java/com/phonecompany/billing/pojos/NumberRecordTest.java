package com.phonecompany.billing.pojos;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class NumberRecordTest {

    private final SoftAssertions softAssertions = new SoftAssertions();
    private DateTimeFormatter formatter;

    @Test
    public void calculateCallPriceValid() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse("13-01-2020 18:10:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 18:12:57", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(1.0), Duration.ofSeconds(162));
    }

    @Test
    public void calculateCallStartingShortlyBeforeHighPriceStarts() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("13-01-2020 07:58:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 08:01:15", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(2.0), Duration.ofSeconds(180));
    }

    @Test
    public void calculateCallStartingShortlyBeforeHighPriceEnds() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("13-01-2020 15:58:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 16:01:15", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(2.5), Duration.ofSeconds(180));
    }

    @Test
    public void callBeforeHigh() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("13-01-2020 00:58:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 01:01:15", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(1.5), Duration.ofSeconds(180));
    }

    @Test
    public void callAfterHigh() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("13-01-2020 16:58:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 17:01:15", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(1.5), Duration.ofSeconds(180));
    }

    @Test
    public void callDuringHigh() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("13-01-2020 09:58:15", formatter);
        LocalDateTime end = LocalDateTime.parse("13-01-2020 10:01:15", formatter);

        NumberRecord callRecord = new NumberRecord(1);
        callRecord.addCall(start, end);

        checkCallRecord(callRecord, BigDecimal.valueOf(3.0), Duration.ofSeconds(180));
    }


    private void checkCallRecord(NumberRecord record, BigDecimal price, Duration duration) {
        softAssertions.assertThat(record.getPrice()).isEqualTo(price);
        softAssertions.assertThat(record.getTimeInCall()).isEqualTo(duration);
        softAssertions.assertAll();
    }
}