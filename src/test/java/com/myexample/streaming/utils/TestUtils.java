package com.myexample.streaming.utils;

import java.util.Collections;
import java.util.List;

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.entity.Payment;

public class TestUtils {
	
	public static List<Account> account() {
		Account account = new Account();
		account.setUsername("abc");
		account.setCardNumber("5105105105105100");
		return Collections.singletonList(account);
	}
	
	public static Payment payment(String cardNumber) {
		Payment payment = new Payment();
		payment.setCardNumber(cardNumber);
		payment.setAmount("101");
		return payment;
	}
	
}
