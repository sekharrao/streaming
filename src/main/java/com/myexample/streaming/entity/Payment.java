package com.myexample.streaming.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Entity
@Data
public class Payment {
	
	@Id
	@GeneratedValue
	Long id;
	
	@CreditCardNumber
	String cardNumber;
	
	@Pattern(regexp="\\d{3}")
	String	amount;
}
