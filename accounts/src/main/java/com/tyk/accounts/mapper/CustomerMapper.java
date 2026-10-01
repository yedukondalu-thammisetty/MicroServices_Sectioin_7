package com.tyk.accounts.mapper;

import com.tyk.accounts.dto.CustomerDto;
import com.tyk.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapCustomerToCustomerDto(Customer customer,CustomerDto customerDto) {

        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer ) {
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
