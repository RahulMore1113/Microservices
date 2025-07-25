package com.rahul.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)

@Data
public class AccountsDto {

    @Schema(
            name = "Account Number",
            description = "Account Number of Eazy Bank Account",
            example = "1234567890"
    )
    @NotEmpty(message = "Account Number cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account Number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account Type of Eazy Bank Account",
            example = "Savings"
    )
    @NotEmpty(message = "Account Type cannot be a null or empty")
    private String accountType;

    @Schema(
            description = "Eazy Bank Address",
            example = "New York"
    )
    @NotEmpty(message = "Branch Address cannot be a null or empty")
    private String branchAddress;

}
