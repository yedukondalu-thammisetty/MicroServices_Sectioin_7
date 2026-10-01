package com.tyk.accounts.service;

import com.tyk.accounts.dto.CustomerDto;

public interface IAccountsService {
    void createCustomer(CustomerDto customerDto);
    
    CustomerDto fetchCustomerDetails(String mobileNumber);
    
    boolean updateCustomerDetails(CustomerDto customerDto);
    
    boolean deleteCustomer(String mobileNumber);
}
