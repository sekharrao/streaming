package com.myexample.streaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myexample.streaming.entity.Payment;
import com.myexample.streaming.exception.AccountNotExistException;
import com.myexample.streaming.repo.AccountRepo;
import com.myexample.streaming.repo.PaymentRepo;


@Service
public class PaymentService {
	
	@Autowired private PaymentRepo paymentRepo;
	
	@Autowired private AccountRepo accRepo;
	
	public Payment createPayment(Payment payment) throws Exception {
		checkAccountExist(payment.getCardNumber());
		
		return paymentRepo.save(payment);
	}
	
	private void checkAccountExist(String cardNumber) throws AccountNotExistException {
		if (accRepo.findByCardNumber(cardNumber).isEmpty()) {
			throw new AccountNotExistException("account exist");
		}
		
	}
	
	public List<Payment> getPayments(){
		return paymentRepo.findAll();
	}
	
}
