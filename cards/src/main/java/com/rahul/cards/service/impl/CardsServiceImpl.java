package com.rahul.cards.service.impl;

import com.rahul.cards.constants.CardsConstants;
import com.rahul.cards.dto.CardsDto;
import com.rahul.cards.entity.Cards;
import com.rahul.cards.exception.CardAlreadyExistsException;
import com.rahul.cards.exception.ResourceNotFoundException;
import com.rahul.cards.mapper.CardsMapper;
import com.rahul.cards.repo.CardsRepo;
import com.rahul.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepo cardsRepo;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards = cardsRepo.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent())
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);

        cardsRepo.save(createNewCard(mobileNumber));

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
    public CardsDto fetchCard(String mobileNumber) {

        Cards cards = cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );

        return CardsMapper.mapToCardsDto(cards, new CardsDto());

    }

    @Override
    public Boolean updateCard(CardsDto cardsDto) {

        Cards cards = cardsRepo.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", cardsDto.getMobileNumber())
        );

        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepo.save(cards);

        return true;

    }

    @Override
    public Boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        cardsRepo.deleteById(cards.getCardId());

        return true;

    }

}
