package com.myexample.streaming.resource;

import static com.myexample.streaming.utils.TestUtils.account;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.exception.AccountExistException;
import com.myexample.streaming.exception.AgeException;
import com.myexample.streaming.service.AccountService;

@ExtendWith(MockitoExtension.class)
class RegistrationTest {
	
	@Mock
	private AccountService	service;
	@InjectMocks
	private Registration	registration;
	
	@Test
	void registerAccount() throws AccountExistException, AgeException {
		when(service.createAccount(any(Account.class))).thenReturn(account().get(0));
		
		ResponseEntity<Object> response = registration.register(account().get(0));
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		verify(service, times(1)).createAccount(any(Account.class));
	}
	
	@Test
	void registerAccountSecondTime() throws AccountExistException, AgeException {
		when(service.createAccount(any(Account.class))).thenThrow(new AccountExistException("duplicate account"));
		
		ResponseEntity<Object> response = registration.register(account().get(0));
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		verify(service, times(1)).createAccount(any(Account.class));
	}
	
	@Test
	void registerAccountForUnderAgePerson() throws AccountExistException, AgeException {
		when(service.createAccount(any(Account.class))).thenThrow(new AgeException("underage"));
		
		ResponseEntity<Object> response = registration.register(account().get(0));
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		verify(service, times(1)).createAccount(any(Account.class));
	}
	
	@Test
	void getRegisteredAccounts() throws AccountExistException, AgeException {
		when(service.getAccounts()).thenReturn(account());
		List<Account> response = registration.getAll();
		
		assertThat(response.size()).isEqualTo(1);
		verify(service, times(1)).getAccounts();
	}
	
}
