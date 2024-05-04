package com.microservices.account.service.Impl;

import com.microservices.account.constants.AccountConstants;
import com.microservices.account.exception.CustomerAlreadyExistsException;
import com.microservices.account.exception.CustomerNotFoundException;
import com.microservices.account.mapper.AccountMapper;
import com.microservices.account.mapper.CustomerMapper;
import com.microservices.account.model.dto.AccountDto;
import com.microservices.account.model.dto.CustomerDto;
import com.microservices.account.model.entity.Account;
import com.microservices.account.model.entity.Customer;
import com.microservices.account.repository.AccountRepository;
import com.microservices.account.repository.CustomerRepository;
import com.microservices.account.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    AccountRepository accountRepository;
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number " + customerDto.getMobileNumber());
        }
        Customer customer = new Customer();
        CustomerMapper.mapToCustomer(customerDto , customer);
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));


    }

    @Override
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {
        Customer optionalCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomerNotFoundException("Customer", "MobileNumber", mobileNumber)
        );

        CustomerDto customerDto = new CustomerDto();
        CustomerMapper.mapToCustomerDto(optionalCustomer , customerDto);
        Account optionalAccount = accountRepository.findByCustomerId(optionalCustomer.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("Account" , "CustomerId" , optionalCustomer.getCustomerId().toString())
        );
        AccountDto accountDto = new AccountDto();


        AccountMapper.mapToAccountDto(optionalAccount , accountDto);
        customerDto.setAccountDto(accountDto);
        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        boolean isUpdated = false;
        if(accountDto != null){
            Account account = accountRepository.findById(customerDto.getAccountDto().getAccountNumber()).orElseThrow(
                    () -> new CustomerNotFoundException(
                            "Account" ,
                            "AccountNumber" ,
                            customerDto.getAccountDto().getAccountNumber().toString())
            );

            AccountMapper.mapToAccount(accountDto , account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new CustomerNotFoundException("Customer" , "CustomerId" , customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto , customer);
            customerRepository.save(customer);
            isUpdated = true;
            return isUpdated;
        }


        return isUpdated;




    }


    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new CustomerNotFoundException("Customer" , "mobileNumber" , mobileNumber)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.delete(customer);

        return true;
    }


    public Account createNewAccount (Customer customer){
        Account newAccount = new Account();
        newAccount.setAccountType(AccountConstants.SAVINGS);
        Long accountNumber  = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCustomerId(customer.getCustomerId());
        return newAccount;

    }


}
