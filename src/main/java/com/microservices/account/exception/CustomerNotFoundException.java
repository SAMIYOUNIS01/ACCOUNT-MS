package com.microservices.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String resourceName , String fieldName , String fieldValue){
        super(String.format("%s not found with given input data %s : %s" , resourceName , fieldName , fieldValue));
    }


}
