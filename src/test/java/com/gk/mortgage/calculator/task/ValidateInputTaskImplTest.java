package com.gk.mortgage.calculator.task;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gk.mortgage.calculator.domain.Messages;
import com.gk.mortgage.calculator.domain.MortgageCalculatorRequest;
import com.gk.mortgage.calculator.exceptions.BadRequestInputException;

public class ValidateInputTaskImplTest {

	@Test
	public void testValidInputRequest() {
		// given
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().type("Fixed").interestRate(5.5)
				.principal(100000.0).term(30).build();

		// when
		ValidateInputTask validateInputTask = new ValidateInputTaskImpl(new Messages());
		validateInputTask.validate(request);

		// test passed if no exception is thrown in validate
		assertTrue(true);
	}

	// @Test(expected = BadRequestInputException.class)
	@Test
	public void testRequestIsNullShouldThrowException() {
		// given
		MortgageCalculatorRequest request = null;

		// then
		assertThrows(BadRequestInputException.class, () -> {
			// when
			ValidateInputTask validateInputTask = new ValidateInputTaskImpl(new Messages());
			validateInputTask.validate(request);
		});
	}

	// @Test(expected = BadRequestInputException.class)
	@Test
	public void testRequestNoPrincipalShouldThrowException() {
		// given
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().type("Fixed").interestRate(5.5).term(30)
				.build();

		// then
		assertThrows(BadRequestInputException.class, () -> {
			// when
			ValidateInputTask validateInputTask = new ValidateInputTaskImpl(new Messages());
			validateInputTask.validate(request);
		});
	}

	@Test
	public void testRequestNoTermShouldThrowException() {
		// given
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().type("fixed").interestRate(5.5)
				.principal(100000.0).build();

		// then
		assertThrows(BadRequestInputException.class, () -> {
			// when
			ValidateInputTask validateInputTask = new ValidateInputTaskImpl(new Messages());
			validateInputTask.validate(request);
		});
	}

}
