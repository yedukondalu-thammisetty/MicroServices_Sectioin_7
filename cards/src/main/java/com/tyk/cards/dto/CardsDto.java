package com.tyk.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardsDto {

	@NotEmpty(message = "Mobile Number cant be a null or empty")
	@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
	private  String mobileNumber;
	
	@NotEmpty(message = "Card Type cont be a null or empty")
	private String cardType;
	
	@NotEmpty(message = "Card Number cont be a null or empty")
	@Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 10 digits")
	private String cardNumber;
	
	@NotNull(message = "Total Limit cont be null or empty")
	@PositiveOrZero(message = " Total Limit cont be a negative value")
	private Integer totalLimit;
	
	@NotNull(message = "Available Amount cont be null or empty")
	@PositiveOrZero(message = " Available Amount cont be a negative value")
	private Integer availableAmount;
	
	@NotNull(message = "AmmountUsed cont be null or empty")
	@PositiveOrZero(message = " amount used cont be a negative value")
	private Integer amountUsed;
	
}
