package com.tyk.cards.controller;

import com.tyk.cards.dto.AccountsContactInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyk.cards.constants.CardsConstants;
import com.tyk.cards.dto.CardsDto;
import com.tyk.cards.dto.ResponseDto;
import com.tyk.cards.service.ICardsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api")
@Validated
public class CardsController {

	private ICardsService cardsService;

	@Autowired
	private AccountsContactInfoDto accountsContactInfoDto;

	CardsController(ICardsService cardsService) {
		this.cardsService = cardsService;
	}

	@GetMapping("/create")
	public ResponseEntity<ResponseDto> createCard( @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number  must be 10 digits") String mobileNumber) {
		cardsService.createCard(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
	}

	@GetMapping("/fetch-card")
	public ResponseEntity<CardsDto> fetchCardDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number  must be 10 digits") String mobileNumber) {

		CardsDto fetchCardDetails = cardsService.fetchCardDetails(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(fetchCardDetails);
	}

	@PutMapping("/update-card")
	public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
		boolean updatedCardDetails = cardsService.updatedCardDetails(cardsDto);
		if (updatedCardDetails) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete-card")
	public ResponseEntity<ResponseDto> deleteCard(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number  must be 10 digits") String mobileNumber) {
		boolean deleteCard = cardsService.deleteCard(mobileNumber);
		if (deleteCard) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
		}
	}
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
	}
}
