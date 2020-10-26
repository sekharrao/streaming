package com.myexample.streaming.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Entity
@Data
@Validated
public class Account {
	
	@Id
	@Pattern(regexp="[a-zA-Z]*")
	String username;
	
	@Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,}$")
	String password;
	
	@Email
	String email;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	String dob;
	
	
	@CreditCardNumber
	String cardNumber;
	
}
