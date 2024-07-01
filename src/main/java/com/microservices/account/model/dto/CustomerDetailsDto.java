package com.microservices.account.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold customer , account , cards and loans information"
)
public class CustomerDetailsDto {

    @Schema(description = "Name of the customer" , example = "Sammy Younis")
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5 , max = 30, message = "the length of customer name should be between 5 and 30")
    private String name;

    @Schema(description = "E-mail of the customer" , example = "sammyYounis@example.com")
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "email address must be a valid value")
    private String email;

    @Schema(description = "Mobile number of the customer" , example = "0123456789")
    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Accounts details for the customer")
    private AccountDto accountDto;

    @Schema(description = "Cards Details for the customer")
    private CardsDto cardsDto;


    @Schema(description = "Loans Details for the customer")
    private LoansDto loansDto;
}
