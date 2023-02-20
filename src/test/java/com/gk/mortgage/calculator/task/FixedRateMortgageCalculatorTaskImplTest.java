package com.gk.mortgage.calculator.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class FixedRateMortgageCalculatorTaskImplTest {
	
	
	@Test
	public void testCalculate30YearMonthlyPayment() {
		// given
		double principal = 100000.0;
		double intRate = 6.0;
		int term = 30;
		
		// when we invoke calculate method
		FixedRateMortgageCalculatorTaskImpl calculator = new FixedRateMortgageCalculatorTaskImpl();
		double monthlyPayment = calculator.calculateMonthlyPayment(principal, intRate, term);
		
		// then
		assertEquals(Double.valueOf(599.55), Double.valueOf(monthlyPayment));
	}
	
	@Test
	public void testCalculate15YearMonthlyPayment() {
		// given
		double principal = 100000.0;
		double intRate = 6.0;
		int term = 15;
		
		// when we invoke calculate method
		FixedRateMortgageCalculatorTaskImpl calculator = new FixedRateMortgageCalculatorTaskImpl();
		double monthlyPayment = calculator.calculateMonthlyPayment(principal, intRate, term);
		
		// then
		assertEquals(Double.valueOf(843.86), Double.valueOf(monthlyPayment));
	}
}
