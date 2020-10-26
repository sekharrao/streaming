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

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.exception.AccountExistException;
import com.myexample.streaming.exception.AgeException;
import com.myexample.streaming.repo.AccountRepo;
import com.myexample.streaming.validator.AccountValidator;

@RestController
public class Registration {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@PostMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody Account acc) {
		Account savedAccount = null;
		try {
			checkAccountExist(acc.getUsername());
			AccountValidator.checkAge(acc.getDob());
			savedAccount = accountRepo.save(acc);
		} catch (AccountExistException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		} catch (AgeException e) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		} catch (TransactionSystemException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}
	
	@GetMapping("/registration")
	public List<Account> getAll() {
		
		return accountRepo.findAll();
		
	}
	
	public void checkAccountExist(String username) throws AccountExistException {
		if (accountRepo.findById(username).isPresent()) {
			throw new AccountExistException("account exist");
		}
		
	}
	
}
