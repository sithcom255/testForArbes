package com.phonecompany.billing;

import com.phonecompany.billing.pojos.NumberRecord;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HonzuvTelephoneBillingCalculator implements TelephoneBillCalculator{

    @Override
    public BigDecimal calculate(String phoneLog) {
        return null;
    }

    private NumberRecord parsePhoneLogLine(String record) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(record, formatter);
        LocalDateTime end = LocalDateTime.parse(record, formatter);

        return new NumberRecord(1);
    }


}
