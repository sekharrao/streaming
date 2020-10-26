package com.myexample.streaming.service;

import static com.myexample.streaming.utils.TestUtils.account;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myexample.streaming.entity.Account;
import com.myexample.streaming.exception.AccountExistException;
import com.myexample.streaming.exception.AgeException;
import com.myexample.streaming.repo.AccountRepo;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
	
	@Mock
	private AccountRepo		repo;
	@InjectMocks
	private AccountService	service;
	
	@Test
	void createAccountForValidDetails() throws AccountExistException, AgeException {
		Account acc = account().get(0);
		when(repo.save(any(Account.class))).thenReturn(acc);
		assertThat(service.createAccount(acc)).isEqualTo(acc);
		verify(repo, times(1)).save(any(Account.class));
		verify(repo, times(1)).findById("abc");
	}
	
	@Test
	void createAccountForValidDetailsSecondTime() throws AccountExistException, AgeException {
		Account acc = account().get(0);
		when(repo.findById("abc")).thenReturn(Optional.of(acc));
		
		assertThatThrownBy(() -> service.createAccount(acc))
		        .isInstanceOf(AccountExistException.class);
		
		verify(repo, times(0)).save(any(Account.class));
		verify(repo, times(1)).findById("abc");
	}
	
	@Test
	void createAccountForUnderAge() throws AccountExistException, AgeException {
		Account acc = account().get(0);
		acc.setDob("2020-10-20");
		
		assertThatThrownBy(() -> service.createAccount(acc))
		        .isInstanceOf(AgeException.class);
		
		verify(repo, times(0)).save(any(Account.class));
		verify(repo, times(1)).findById("abc");
	}
	
	@Test
	void getAccounts() throws AccountExistException, AgeException {
		when(repo.findAll()).thenReturn(account());
		assertThat(service.getAccounts().size()).isEqualTo(1);
		verify(repo, times(1)).findAll();
	}
}
