package com.tyk.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tyk.cards.constants.CardsConstants;
import com.tyk.cards.dto.CardsDto;
import com.tyk.cards.entity.Cards;
import com.tyk.cards.exception.CardAlreadyExistsException;
import com.tyk.cards.exception.ResourceNotFoundException;
import com.tyk.cards.mapper.CardsMapper;
import com.tyk.cards.repository.CardsRepository;
import com.tyk.cards.service.ICardsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {
	
	private CardsRepository cardsRepository;

	@Override
	public void createCard(String mobileNumber) {
		
		Optional<Cards> byMobileNumber = cardsRepository.findByMobileNumber(mobileNumber);
		if(byMobileNumber.isPresent()) {
			throw new CardAlreadyExistsException(mobileNumber + "With this  Mobile Number already having thecard");
		}
		Cards save = cardsRepository.save(createNewCard(mobileNumber));
		System.out.println(save);
		
	}
 
	private Cards createNewCard(String mobileNumber) {
		Cards newCard = new Cards();
		long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
		newCard.setCardNumber(Long.toString(randomCardNumber));
		newCard.setMobileNumber(mobileNumber);
		newCard.setCardType(CardsConstants.CREDIT_CARD);
		newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
		newCard.setAmountUsed(0);
		newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
		return newCard;
	}
	@Override
	public CardsDto fetchCardDetails(String mobileNumber) {
		Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));
		CardsDto mapToCardsDto = CardsMapper.mapToCardsDto(card, new CardsDto());
		return mapToCardsDto;
	}

	@Override
	public boolean updatedCardDetails(CardsDto cardsDto) {
		Cards existingCard = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(()-> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
		Cards mapToCards = CardsMapper.mapToCards(cardsDto, existingCard);
		Cards save = cardsRepository.save(mapToCards);
		System.out.println(save);
		return true;
	}

	@Override
	public boolean deleteCard(String mobileNumber) {
		Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber));
		cardsRepository.deleteById(card.getCardsId());
		return true;
	}
	
	
	

}
