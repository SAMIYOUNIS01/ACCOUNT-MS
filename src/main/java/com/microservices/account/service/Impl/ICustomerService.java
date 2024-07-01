package com.microservices.account.service.Impl;

import com.microservices.account.model.dto.CustomerDetailsDto;

public interface ICustomerService {


    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
