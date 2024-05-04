package com.microservices.account.service;

import com.microservices.account.model.dto.CustomerDto;

public interface IAccountService {


    void createAccount(CustomerDto customerDto);

    CustomerDto getCustomerByMobileNumber(String mobileNumber);

    boolean updateAccount (CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
