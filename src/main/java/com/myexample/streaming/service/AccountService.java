package com.myexample.streaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.exception.AccountExistException;
import com.myexample.streaming.exception.AgeException;
import com.myexample.streaming.repo.AccountRepo;
import com.myexample.streaming.validator.AccountValidator;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	public Account createAccount(Account acc) throws AccountExistException, AgeException {
		Account savedAccount;
		checkAccountExist(acc.getUsername());
		AccountValidator.checkAge(acc.getDob());
		savedAccount = accountRepo.save(acc);
		return savedAccount;
	}
	
	private void checkAccountExist(String username) throws AccountExistException {
		if (accountRepo.findById(username).isPresent()) {
			throw new AccountExistException("account exist");
		}
		
	}
	
	public List<Account> getAccounts() {
		return accountRepo.findAll();
	}
	
}
