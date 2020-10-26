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

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.exception.AccountExistException;
import com.myexample.streaming.exception.AgeException;
import com.myexample.streaming.service.AccountService;

@RestController
public class Registration {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody @Valid Account acc) {
		Account savedAccount = null;
		try {
			savedAccount = accountService.createAccount(acc);
		} catch (AccountExistException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (AgeException e) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}
	
	@GetMapping("/registration")
	public List<Account> getAll() {
		return accountService.getAccounts();
	}
	
}
