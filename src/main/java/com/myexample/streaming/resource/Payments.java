package com.myexample.streaming.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myexample.streaming.entity.Payment;
import com.myexample.streaming.exception.AccountNotExistException;
import com.myexample.streaming.repo.AccountRepo;
import com.myexample.streaming.repo.PaymentRepo;

@RestController
public class Payments {
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private AccountRepo accRepo;
	
	@PostMapping("/payments")
	public ResponseEntity<Object> payment(@RequestBody Payment payment) {
		Payment savedPayment = null;
		
		try {
			checkAccountExist(payment.getCardNumber());
			
			savedPayment = paymentRepo.save(payment);
		} catch (AccountNotExistException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (TransactionSystemException e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
	}
	
	@GetMapping("/payments")
	public List<Payment> getAll() {
		
		return paymentRepo.findAll();
		
	}
	
	public void checkAccountExist(String cardNumber) throws AccountNotExistException {
		if (accRepo.findByCardNumber(cardNumber).isEmpty()) {
			throw new AccountNotExistException("account exist");
		}
		
	}
	
}
