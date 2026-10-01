package com.tyk.cards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cards extends BaseEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long cardsId;
	
	private String mobileNumber;
	
	private String cardNumber;
	
	private String cardType;
	
	private Integer totalLimit;
	
	private Integer availableAmount;
	
	private Integer amountUsed;
	
	
}
