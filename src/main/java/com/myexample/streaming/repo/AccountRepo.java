package com.myexample.streaming.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myexample.streaming.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account,String>{

	List<Account> findByCardNumber(String cardNumber);

	
}
