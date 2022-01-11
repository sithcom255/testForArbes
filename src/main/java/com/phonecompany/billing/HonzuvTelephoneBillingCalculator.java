package com.phonecompany.billing;

import com.phonecompany.billing.pojos.NumberRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * there is some javadoc missing ahead of you
 */
public class HonzuvTelephoneBillingCalculator implements TelephoneBillCalculator {

    Long maxNumber = 0l;
    Map<Long, NumberRecord> numbers = new TreeMap<>();

    @Override
    public BigDecimal calculate(String phoneLog) {
        phoneLog.lines().forEach(this::parsePhoneLogLine);
        return statementCalculation();
    }

    private void parsePhoneLogLine(String record) {
        String[] recordValues = record.split(",");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        long number = Long.parseLong(recordValues[0]);
        LocalDateTime start = LocalDateTime.parse(recordValues[1], formatter);
        LocalDateTime end = LocalDateTime.parse(recordValues[2], formatter);

        if(numbers.containsKey(number)){
            numbers.get(number).addCall(start, end);
        } else {
            NumberRecord numberRecord = new NumberRecord(number);
            numberRecord.addCall(start, end);
            numbers.put(number, numberRecord);
        }

        if(numbers.get(number).isGreater(numbers.get(maxNumber))) {
            this.maxNumber = number;
        }
    }

    private BigDecimal statementCalculation() {
        BigDecimal finalPrice = BigDecimal.valueOf(0);
        if(!Objects.isNull(maxNumber)) {
            numbers.remove(maxNumber);
        }
        for (Map.Entry<Long, NumberRecord> entry: numbers.entrySet()) {
            finalPrice = finalPrice.add(entry.getValue().getPrice());
        }
        return finalPrice;
    }
}
