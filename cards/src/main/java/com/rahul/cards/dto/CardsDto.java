package com.rahul.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold Cards Information"
)
@Data
public class CardsDto {

    @Schema(
            description = "Mobile Number of Customer",
            example = "1234567890"
    )
    @NotEmpty(message = "Mobile Number cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Card Number of Customer",
            example = "123456789012"
    )
    @NotEmpty(message = "Card Number cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card Number must be 12 digits")
    private String cardNumber;

    @Schema(
            description = "Type of the Card",
            example = "Credit Card"
    )
    @NotEmpty(message = "Card Type cannot be a null or empty")
    private String cardType;

    @Schema(
            description = "Total amount limit available against a card",
            example = "100000"
    )
    @Positive(message = "Total Card limit should be greater than zero")
    private Integer totalLimit;

    @Schema(
            description = "Total amount used by a Customer",
            example = "1000"
    )
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private Integer amountUsed;

    @Schema(
            description = "Total available amount against a card",
            example = "90000"
    )
    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    private Integer availableAmount;

}
