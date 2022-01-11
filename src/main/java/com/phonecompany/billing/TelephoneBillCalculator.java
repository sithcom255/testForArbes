package com.phonecompany.billing;


import java.math.BigDecimal;

public interface TelephoneBillCalculator {

    public BigDecimal calculate(String phoneLog);
}
