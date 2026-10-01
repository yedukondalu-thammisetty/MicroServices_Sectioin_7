package com.tyk.loans.mapper;

import com.tyk.loans.dto.LoansDto;
import com.tyk.loans.entity.Loans;

public class LoansMapper {

	public static LoansDto toMapLoansDto(Loans loans, LoansDto loansDto) {

		loansDto.setAmountPaid(loans.getAmountPaid());
		loansDto.setLoanNumber(loans.getLoanNumber());
		loansDto.setLoanType(loans.getLoanType());
		loansDto.setMobileNumber(loans.getMobileNumber());
		loansDto.setOutstandingmount(loans.getOutstandingAmount());
		loansDto.setTotalLoan(loans.getTotalLoan());
		return loansDto;
	}

	public static Loans toMapLoans(LoansDto loansDto, Loans loans) {

		loans.setAmountPaid(loansDto.getAmountPaid());
		loans.setLoanNumber(loansDto.getLoanNumber());
		loans.setMobileNumber(loansDto.getMobileNumber());
		loans.setLoanType(loansDto.getLoanType());
		loans.setOutstandingAmount(loansDto.getOutstandingmount());
		loans.setTotalLoan(loansDto.getTotalLoan());
		return loans;
	}

}
