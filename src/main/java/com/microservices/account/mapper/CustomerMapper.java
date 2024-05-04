package com.microservices.account.mapper;

import com.microservices.account.model.dto.CustomerDto;
import com.microservices.account.model.entity.Customer;

public class CustomerMapper {


    public static CustomerDto mapToCustomerDto (Customer customer , CustomerDto customerDto){
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }


    public static Customer mapToCustomer (CustomerDto customerDto , Customer customer){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
