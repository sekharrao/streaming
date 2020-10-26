package com.myexample.streaming.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myexample.streaming.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

	
}
