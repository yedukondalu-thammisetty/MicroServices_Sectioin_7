package com.tyk.cards.service;

import com.tyk.cards.dto.CardsDto;

public interface ICardsService {
	
	void createCard(String mobileNumber);
	
	CardsDto fetchCardDetails(String mobileNumber);
	
	boolean updatedCardDetails(CardsDto cardsDto);
	
	boolean deleteCard(String mobileNumber);

}
