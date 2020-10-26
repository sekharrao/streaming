package com.myexample.streaming.service;

import static com.myexample.streaming.utils.TestUtils.account;
import static com.myexample.streaming.utils.TestUtils.payment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myexample.streaming.entity.Payment;
import com.myexample.streaming.exception.AccountNotExistException;
import com.myexample.streaming.repo.AccountRepo;
import com.myexample.streaming.repo.PaymentRepo;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
	
	@Mock
	private PaymentRepo paymentRepo;
	
	@Mock
	private AccountRepo accRepo;
	
	@InjectMocks
	private PaymentService service;
	
	@Test
	void createPaymetForValidAccount() throws Exception {
		when(accRepo.findByCardNumber(anyString())).thenReturn(account());
		when(paymentRepo.save(any(Payment.class))).thenReturn(payment("5105105105105100"));
		Payment createPayment = service.createPayment(payment("5105105105105100"));
		String cardNumber = createPayment.getCardNumber();
		
		assertThat(cardNumber).isEqualTo("5105105105105100");
	}
	
	@Test
	void createPaymetForValidInvalidAccount() throws Exception {
		when(accRepo.findByCardNumber(anyString())).thenReturn(new ArrayList<>());
		
		assertThatThrownBy(() -> service.createPayment(payment("5105105105105101"))).isInstanceOf(AccountNotExistException.class);
		verify(accRepo, times(1)).findByCardNumber(anyString());
		verify(paymentRepo, times(0)).save(any());
	}
	
}
