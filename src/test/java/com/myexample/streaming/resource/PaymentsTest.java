package com.myexample.streaming.resource;

import static com.myexample.streaming.utils.TestUtils.payment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myexample.streaming.entity.Payment;
import com.myexample.streaming.exception.AccountNotExistException;
import com.myexample.streaming.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentsTest {
	
	@Mock
	private PaymentService service;
	
	@InjectMocks
	private Payments payments;
	
	@Test
	void createPaymentsForExistingAccount() throws Exception {
		String cardNumber = "12321312";
		when(service.createPayment(any(Payment.class))).thenReturn(payment(cardNumber));
		Payment payment = payment(cardNumber);
		ResponseEntity<Object> response = payments.makePayment(payment);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().toString()).contains(cardNumber);
		verify(service, times(1)).createPayment(any(Payment.class));
	}
	
	@Test
	void createPaymentsForNonExistingAccount() throws Exception {
		
		when(service.createPayment(any(Payment.class))).thenThrow(new AccountNotExistException("not found"));
		
		Payment payment = payment("12321312");
		ResponseEntity<Object> response = payments.makePayment(payment);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		verify(service, times(1)).createPayment(any(Payment.class));
	}
	
}
