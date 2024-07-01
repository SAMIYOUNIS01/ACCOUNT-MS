package com.microservices.account.controller;

import com.microservices.account.constants.AccountConstants;
import com.microservices.account.model.dto.AccountContactInfoDto;
import com.microservices.account.model.dto.CustomerDto;
import com.microservices.account.model.dto.ErrorResponseDto;
import com.microservices.account.model.dto.ResponseDto;
import com.microservices.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@EnableFeignClients 
@RequestMapping(path = "/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST APIs for Account in bank",
        description = "Create , Update , Read , Delete (Operations)"
)
public class AccountController {

    private final IAccountService iAccountService;
    @Value("${build.version}")
    private String buildVersion;
    @Autowired
    private Environment environment;
    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    public AccountController(IAccountService iAccountService){
        this.iAccountService = iAccountService;
    }


    @Operation(
            summary = "Create Account REST API",
            description = "create new customer and account inside bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP STATUS CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount (@Valid @RequestBody CustomerDto customerDto){
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch customer and account details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerByMobileNumber(
            @Pattern (regexp = "(^$|[0-9]{10})" , message = "Mobile Number must be 10 digits")
            @RequestParam
            String mobileNumber){
        CustomerDto customerDto = iAccountService.getCustomerByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "UPDATE Account Details REST API",
            description = "REST API to UPDATE customer and account details based on account number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "417",
            description = "HTTP STATUS EXPECTATION FAILED"

    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails (@Valid @RequestBody CustomerDto customerDto){
        boolean result = iAccountService.updateAccount(customerDto);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(AccountConstants.STATUS_200 , AccountConstants.MESSAGE_200)
            );
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                new ResponseDto(AccountConstants.STATUS_417 , AccountConstants.MESSAGE_417_UPDATE)
        );
    }

    @Operation(
            summary = "DELETE Account Details REST API",
            description = "REST API to DELETE customer and account details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR"
    )
    @ApiResponse(
            responseCode = "417",
            description = "HTTP STATUS EXPECTATION FAILED"

    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount (
            @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile number must be 10 digits")
            @RequestParam
            String mobileNumber){
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(AccountConstants.STATUS_200 , AccountConstants.MESSAGE_200)
            );
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                new ResponseDto(AccountConstants.STATUS_417 , AccountConstants.MESSAGE_417_DELETE)
        );
    }

    @Operation(
            summary = "Get Build Version REST API",
            description = "REST API to get build version for account microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity
                .status(
                        HttpStatus.OK
                )
                .body(
                        buildVersion
                );
    }



    @Operation(
            summary = "Get Java Version REST API",
            description = "REST API to get java version for account microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(
                        HttpStatus.OK
                )
                .body(
                        environment.getProperty("JAVA_HOME")
                );
    }



    @Operation(
            summary = "Get Contact Information REST API",
            description = "REST API to get contact information for account microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactDetails(){
        return ResponseEntity
                .status(
                        HttpStatus.OK
                )
                .body(
                        accountContactInfoDto
                );
    }
}
