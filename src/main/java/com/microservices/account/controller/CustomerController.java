package com.microservices.account.controller;


import com.microservices.account.model.dto.CustomerDetailsDto;
import com.microservices.account.model.dto.ErrorResponseDto;
import com.microservices.account.service.Impl.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RestController
@Tag(
        name = "REST APIs for Customer in bank",
        description = "REST APIs in bank to fetch customer details"
)

public class CustomerController {

    private final ICustomerService iCustomerService;


    public CustomerController(ICustomerService iCustomerService){
        this.iCustomerService = iCustomerService;
    }
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer account , loans and cards details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Http Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile Number must be 10 digits")
            @RequestParam
            String mobileNumber
    ){
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);

    }
}
