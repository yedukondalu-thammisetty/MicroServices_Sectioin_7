package com.tyk.loans.controller;

import com.tyk.loans.dto.AccountsContactInfoDto;
import com.tyk.loans.entity.Loans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyk.loans.constants.LoansConstants;
import com.tyk.loans.dto.LoansDto;
import com.tyk.loans.dto.ResponseDto;
import com.tyk.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Loans Microservices EndPoints",description = "Loans Microservice Controller to Handle Create and Update and Fetch and Deleted Operations")
public class LoansController {
	
	
	private ILoansService loansService;

	@Autowired
	private AccountsContactInfoDto accountsContactInfoDto;

	LoansController(ILoansService loansService) {
		this.loansService = loansService;
	}

	@GetMapping("/create")
	public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		loansService.createLoan(mobileNumber);
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
	}
	
	@GetMapping("/fetch-loan")
	public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		LoansDto fetchLoansDetails = loansService.fetchLoansDetails(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(fetchLoansDetails);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoanDeatils(@Valid @RequestBody LoansDto loansDto){
		
		boolean updateLoanDetails = loansService.updateLoanDetails(loansDto);
		if(updateLoanDetails) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		boolean deleteLoan = loansService.deleteLoan(mobileNumber);
		
		if(deleteLoan) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.MESSAGE_417_DELETE, LoansConstants.MESSAGE_417_DELETE));
		}
	}
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
	}
}
