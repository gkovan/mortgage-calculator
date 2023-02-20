package com.gk.mortgage.calculator.controller;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;

import com.gk.mortgage.calculator.domain.MortgageCalculatorRequest;
import com.gk.mortgage.calculator.domain.MortgageCalculatorResponse;
import com.gk.mortgage.calculator.exceptions.BadRequestInputException;
import com.gk.mortgage.calculator.task.MortgageProcessorTask;
import com.gk.mortgage.calculator.task.ValidateInputTask;

public class MortgageCalculatorControllerTest {
	
	@Mock
	MortgageProcessorTask mortgageProcessorTask;
	
	@Mock
	ValidateInputTask validateInputTask;
	
	@InjectMocks
	MortgageCalculatorController mortgageCalculatorController;

	private AutoCloseable closeable;


	@BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
	
	@Test
	public void controllerShouldReturnProperResponseObject() {
		// setup mock mortgage task
		MortgageCalculatorResponse mockResponse = new MortgageCalculatorResponse();
		mockResponse.setInterestRate(5.0);
		mockResponse.setPrincipal(100000.0);
		mockResponse.setTerm(30);
		mockResponse.setMonthlyPayment(536.82);
		doNothing().when(validateInputTask).validate(any());
		when(mortgageProcessorTask.process(any())).thenReturn(mockResponse);
		
		// given mortgage request
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().
											interestRate(5.0).
											principal(100000.0).
											term(30).
											build();
		
		// when controller is invoked
		MortgageCalculatorResponse response =  mortgageCalculatorController.calculateMonthlyPayment(request);
		
		// then response should be a MorgageCalculatorResponse object
		assertInstanceOf(MortgageCalculatorResponse.class, response);		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void controllerShouldThrowBadRequestInputException() {

		doNothing().when(validateInputTask).validate(any());
		when(mortgageProcessorTask.process(any())).thenThrow(BadRequestInputException.class);
		
		// given mortgage request
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().
				interestRate(5.0).
				term(30).
				build();
		
		// when controller is invoked then expect BadRequestInputException
		assertThrows(BadRequestInputException.class, () -> {

			MortgageCalculatorResponse response =  mortgageCalculatorController.calculateMonthlyPayment(request);
		});
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void controllerShouldThrowRestClientException() {

		doNothing().when(validateInputTask).validate(any());
		when(mortgageProcessorTask.process(any())).thenThrow(RestClientException.class);
		
		// given mortgage request
		MortgageCalculatorRequest request = MortgageCalculatorRequest.builder().
				interestRate(5.0).
				principal(100000.0).
				term(30).
				build();


		// when controller is invoked then expect RestClientException
		assertThrows(RestClientException.class, () -> {
			MortgageCalculatorResponse response =  mortgageCalculatorController.calculateMonthlyPayment(request);
		});
	}
}
