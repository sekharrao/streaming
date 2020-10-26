package com.myexample.streaming.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.myexample.streaming.exception.AgeException;


public class AccountValidator {

	
	public static void checkAge(String dob) throws AgeException {
		
		if( Period.between(LocalDate.parse(dob, DateTimeFormatter.ISO_DATE), LocalDate.now()).getYears() < 18) {
			throw new AgeException("less than 18");
		}
		
	}


}