package com.gk.mortgage.calculator.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gk.mortgage.calculator.MortgageCalculatorApplication;
import com.gk.mortgage.calculator.utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(classes = MortgageCalculatorApplication.class)
@WebAppConfiguration
public class MortgageCalculatorApiTest {
    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setup() throws Exception {

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void invokeMortgageCalculatorInterestOnlyPostRequestShouldBeSuccessHTTP200() throws Exception {

        // given a valid request to calc interest only payment
        String requestBody = TestUtils.loadSourceFile("__files/mortgage-calculator-interest-only-request.json");
        
        String url = "/calculate";

        // when api is invoked then we expect to get a http 200 and valid response data
        mockMvc.perform(post(url).headers(httpHeaders).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))                
                .andExpect(jsonPath("$.principal", Matchers.equalTo(100000.0)))
                .andExpect(jsonPath("$.interestRate", Matchers.equalTo(5.0)))
        		.andExpect(jsonPath("$.term", Matchers.equalTo(30)))
        		.andExpect(jsonPath("$.type", Matchers.equalTo("interest")))
        		.andExpect(jsonPath("$.monthlyPayment", Matchers.equalTo(416.67)));
    }
    
    @Test
    public void invokeMortgageCalculatorFixedRatePostRequestShouldBeSuccessHTTP200() throws Exception {

        // given a valid request to calc fixed rate payment
        String requestBody = TestUtils.loadSourceFile("__files/mortgage-calculator-fixed-rate-request.json");
        
        String url = "/calculate";

        // when api is invoked then we expect to get a http 200 and valid response data
        mockMvc.perform(post(url).headers(httpHeaders).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))                
                .andExpect(jsonPath("$.principal", Matchers.equalTo(100000.0)))
                .andExpect(jsonPath("$.interestRate", Matchers.equalTo(5.0)))
        		.andExpect(jsonPath("$.term", Matchers.equalTo(30)))
        		.andExpect(jsonPath("$.type", Matchers.equalTo("fixed")))
        		.andExpect(jsonPath("$.monthlyPayment", Matchers.equalTo(536.82)));
    }
    
    @Test
    public void invokeMortgageCalculatorWithBadInputNoPrincipalShouldFailWithHTTP400() throws Exception {

        // given a invalid request
        String requestBody = TestUtils.loadSourceFile("__files/mortgage-calculator-bad-input-no-principal-request.json");
        
        String url = "/calculate";

        // when api is invoked then we expect an http 400 response with an appropriate error message
        mockMvc.perform(post(url).headers(httpHeaders).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))                
                .andExpect(jsonPath("$.code", Matchers.equalTo("MC0001")))
                .andExpect(jsonPath("$.message", Matchers.equalTo("MC0001:Request body validation failed. Principal amount does not exist. Resubmit the request body with principal amount.  See: https://github.com/gkovan/mortgage-calculator")));
    }
    
    
    @Test
    public void invokeMortgageCalculatorNoInterestInvokeInterestServiceShouldFailWithHTTP500() throws Exception {

        String requestBody = TestUtils.loadSourceFile("__files/mortgage-calculator-fixed-rate-no-interest-invoke-interest-service-request.json");
        
        String url = "/calculate";

        mockMvc.perform(post(url).headers(httpHeaders).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))                
                .andExpect(jsonPath("$.code", Matchers.equalTo("MC0003")))
                .andExpect(jsonPath("$.message", Matchers.equalTo("MC0003:Error invoking the interest rate service. Resubmit the request again.")));
    }    
}
