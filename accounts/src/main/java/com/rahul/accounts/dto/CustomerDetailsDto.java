package com.rahul.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer, Accounts, Cards & Loans information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the Customer",
            example = "Eazy Bytes"
    )
    @NotEmpty(message = "Name cannot be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 & 30")
    private String name;

    @Schema(
            description = "Email Address of the Customer",
            example = "abc@gmail.com"
    )
    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the Customer",
            example = "0123456789"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account Details of the Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Cards Details of the Customer"
    )
    private CardsDto cardsDto;

    @Schema(
            description = "Loans Details of the Customer"
    )
    private LoansDto loansDto;

}
