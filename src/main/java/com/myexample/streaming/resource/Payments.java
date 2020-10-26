package com.myexample.streaming.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myexample.streaming.entity.Payment;
import com.myexample.streaming.exception.AccountNotExistException;
import com.myexample.streaming.service.PaymentService;

@RestController
public class Payments {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payments")
	public ResponseEntity<Object> makePayment(@RequestBody @Valid Payment payment) {
		Payment savedPayment = null;
		try {
			savedPayment = paymentService.createPayment(payment);
		} catch (AccountNotExistException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
	}
	
	@GetMapping("/payments")
	public List<Payment> getAll() {
		
		return paymentService.getPayments();
		
	}
	
}
