package com.gk.mortgage.calculator.task;

import com.gk.mortgage.calculator.task.MortgageTaskImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gk.mortgage.calculator.domain.MortgageCalculatorRequest;
import com.gk.mortgage.calculator.domain.MortgageCalculatorResponse;

public class MortgageTaskTest {
	
	@Test
	public void testTaskCalculatesCorrectMonthlyMortgagePayment() {
		
		// given request object with principal $100,00, int rate of 5.0% and term is 30 years
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().
				type("fixed").
				interestRate(5.0).
				principal(100000.0).
				term(30).
				build();
		
		// when task is invoked
		MortgageTask mortgageTask = new MortgageTaskImpl();
		MortgageCalculatorResponse response = mortgageTask.calculate(request);
		
		// then 
		assertEquals(536.82, response.getMonthlyPayment(), 0.001);
		
	}

}
