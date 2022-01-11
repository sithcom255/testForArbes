package com.phonecompany.billing;

import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class HonzuvTelephoneBillingCalculatorTest {


    @Test
    public void shoudlIgnoreLongCall(){
        String oneLine = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:05:20,18-01-2020 09:10:00";
        assertEquals(BigDecimal.valueOf(1.0), new HonzuvTelephoneBillingCalculator().calculate(oneLine));
    }

    @Test
    public void multipleCasesTest(){
//        calculation : 1 + 2 + (2.5 + 1)
        String oneLine = "420774577453,13-01-2020 07:58:15,13-01-2020 08:00:15\n" +
                "420774577453,13-01-2020 15:58:15,13-01-2020 16:00:15\n" +
                "420774577453,13-01-2020 17:10:00,13-01-2020 17:20:00\n" +
                "420776562353,18-01-2020 08:05:20,18-01-2020 09:10:00";
        assertEquals(BigDecimal.valueOf(6.5), new HonzuvTelephoneBillingCalculator().calculate(oneLine));
    }

    @Test
    public void oneMoreNumber(){
//        calculation : 1 + 2 + (2.5 + 1) + (2.5 + 1)
        String oneLine = "420774577453,13-01-2020 07:58:15,13-01-2020 08:00:15\n" +
                "420774577453,13-01-2020 15:58:15,13-01-2020 16:00:15\n" +
                "420774577453,13-01-2020 17:10:00,13-01-2020 17:20:00\n" +
                "420774577454,13-01-2020 17:10:00,13-01-2020 17:20:00\n" +
                "420776562353,18-01-2020 08:05:20,18-01-2020 09:10:00";
        assertEquals(BigDecimal.valueOf(10.0), new HonzuvTelephoneBillingCalculator().calculate(oneLine));
    }
}