package com.microservices.account.service.Impl;

import com.microservices.account.exception.CustomerNotFoundException;
import com.microservices.account.mapper.AccountMapper;
import com.microservices.account.mapper.CustomerMapper;
import com.microservices.account.model.dto.AccountDto;
import com.microservices.account.model.dto.CardsDto;
import com.microservices.account.model.dto.CustomerDetailsDto;
import com.microservices.account.model.dto.LoansDto;
import com.microservices.account.model.entity.Accounts;
import com.microservices.account.model.entity.Customer;
import com.microservices.account.repository.AccountRepository;
import com.microservices.account.repository.CustomerRepository;
import com.microservices.account.service.client.CardsFeignClient;
import com.microservices.account.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService{

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer optionalCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomerNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        Accounts optionalAccount = accountRepository.findByCustomerId(optionalCustomer.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("Account" , "CustomerId" , optionalCustomer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetialsDto(optionalCustomer , new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(optionalAccount , new AccountDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansByMobileNumber(mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCard(mobileNumber);

        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
