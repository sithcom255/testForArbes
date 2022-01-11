package com.phonecompany.billing.pojos;

import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NumberRecordTest extends TestCase {

    public void calculateCallPriceValid() {
        String startS = "13-01-2020 18:10:15";
        String endS = "13-01-2020 18:12:57";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(startS, formatter);
        LocalDateTime end = LocalDateTime.parse(endS, formatter);
        NumberRecord numberRecord = new NumberRecord(1);
        numberRecord.addCall(start, end);
    }
}