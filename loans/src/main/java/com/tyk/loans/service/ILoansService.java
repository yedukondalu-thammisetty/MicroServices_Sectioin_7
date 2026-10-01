package com.tyk.loans.service;

import com.tyk.loans.dto.LoansDto;

public interface ILoansService {

	void createLoan(String mobileNumber);
	
	LoansDto fetchLoansDetails(String mobileNumber);
	
	boolean updateLoanDetails(LoansDto loansDto);
	
	boolean deleteLoan(String mobileNumber);
}
