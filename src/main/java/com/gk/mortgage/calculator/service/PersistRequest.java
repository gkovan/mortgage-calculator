package com.gk.mortgage.calculator.service;

import com.gk.mortgage.calculator.domain.MortgageCalculatorDataToPersist;

public interface PersistRequest {
    public String saveToDB(MortgageCalculatorDataToPersist request);
}
