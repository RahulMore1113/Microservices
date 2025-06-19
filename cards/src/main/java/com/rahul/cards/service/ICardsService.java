package com.rahul.cards.service;

import com.rahul.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    Boolean updateCard(CardsDto cardsDto);

    Boolean deleteCard(String mobileNumber);

}
