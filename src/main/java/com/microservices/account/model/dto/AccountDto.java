package com.microservices.account.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
        name = "Account",
        description = "Schema to hold account information"
)
public class AccountDto {

    @Schema(description = "Bank Account Number" , example = "1598756486")
    @NotEmpty(message = "Account Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})" , message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Bank Account Type" , example = "Savings")
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    @Schema(description = "Bank branch address" , example = "123 , Amman Jordan")
    @NotEmpty(message = "Branch address can not be null or empty")
    private String branchAddress;

}
