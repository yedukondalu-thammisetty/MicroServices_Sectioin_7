package com.tyk.loans.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tyk.loans.constants.LoansConstants;
import com.tyk.loans.dto.LoansDto;
import com.tyk.loans.entity.Loans;
import com.tyk.loans.exception.LoanAlreadyExistsException;
import com.tyk.loans.exception.ResourceNotFoundException;
import com.tyk.loans.mapper.LoansMapper;
import com.tyk.loans.repository.LoansRepository;
import com.tyk.loans.service.ILoansService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

	private LoansRepository loansRepository;

	@Override
	public void createLoan(String mobileNumber) {
		 Optional<Loans> byMobileNumber = loansRepository.findByMobileNumber(mobileNumber);
		 if(byMobileNumber.isPresent()) {
			 new LoanAlreadyExistsException(
						"With This " + mobileNumber + " Mobile Number Already having the Loan");
		 }
		Loans save = loansRepository.save(createAccount(mobileNumber));
		System.out.println(save);

	}

	private Loans createAccount(String mobileNumber) {

		Loans loans = new Loans();
		long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
		loans.setLoanNumber(String.valueOf(randomLoanNumber));
		loans.setAmountPaid(BigDecimal.ZERO);
		loans.setLoanType(LoansConstants.HOME_LOAN);
		loans.setOutstandingAmount(new BigDecimal(LoansConstants.NEW_LOAN_LIMIT));
		loans.setTotalLoan(new BigDecimal(LoansConstants.NEW_LOAN_LIMIT));
		loans.setMobileNumber(mobileNumber);

		return loans;

	}

	@Override
	public LoansDto fetchLoansDetails(String mobileNumber) {
		Loans loan = loansRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Loans", "Mobile Number", mobileNumber));
		LoansDto mapLoansDto = LoansMapper.toMapLoansDto(loan, new LoansDto());
		return mapLoansDto;
	}

	@Override
	public boolean updateLoanDetails(LoansDto loansDto) {
		Loans existingLoan = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan Number", loansDto.getLoanNumber()));

		Loans mapLoans = LoansMapper.toMapLoans(loansDto, existingLoan);
		loansRepository.save(mapLoans);
		return true;
	}

	@Override
	public boolean deleteLoan(String mobileNumber) {
		Loans loan = loansRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Loans", "Mobile Number", mobileNumber));
		loansRepository.deleteById(loan.getLoanId());
		return true;
	}

}
