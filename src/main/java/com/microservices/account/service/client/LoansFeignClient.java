package com.microservices.account.service.client;

import com.microservices.account.model.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch" , consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoansByMobileNumber
            (@RequestParam
             String mobileNumber
            );
}
