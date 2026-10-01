package com.tyk.loans.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Loans extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	private String mobileNumber;
	
	private String loanNumber;
	
	private String loanType;
	
	private BigDecimal totalLoan;
	
	private BigDecimal amountPaid;
	
	private BigDecimal outstandingAmount;
	

}
