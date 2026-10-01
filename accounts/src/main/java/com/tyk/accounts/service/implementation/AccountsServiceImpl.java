package com.tyk.accounts.service.implementation;

import com.tyk.accounts.constants.AccountsConstants;
import com.tyk.accounts.dto.AccountsDto;
import com.tyk.accounts.dto.CustomerDto;
import com.tyk.accounts.entity.Accounts;
import com.tyk.accounts.entity.Customer;
import com.tyk.accounts.exception.CustomerAlreadyExistException;
import com.tyk.accounts.exception.ResourceNotFoundException;
import com.tyk.accounts.mapper.AccountsMapper;
import com.tyk.accounts.mapper.CustomerMapper;
import com.tyk.accounts.repository.AccountsRepository;
import com.tyk.accounts.repository.CustomerRepository;
import com.tyk.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createCustomer(CustomerDto customerDto) {

		Customer newCustomer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionCustomer = customerRepository
				.findByMobileNumber(String.valueOf(customerDto.getMobileNumber()));
		if (optionCustomer.isPresent()) {
			throw new CustomerAlreadyExistException(
					"Already Customer Exist with Given Mobile Number" + customerDto.getMobileNumber());
		}
//		newCustomer.setCreatedAt(LocalDateTime.now());
//		newCustomer.setCreatedBy("Anonymous");
		Customer savedCustomer = customerRepository.save(newCustomer);
		accountsRepository.save(createAccount(savedCustomer));
		System.out.println("Account Created");
	}

	private Accounts createAccount(Customer customer) {
		Accounts accounts = new Accounts();
//		accounts.setCreatedAt(LocalDateTime.now());
//		accounts.setCreatedBy("Anonymous");
		accounts.setCustomerId(customer.getCustomerId());
		accounts.setAccountType(AccountsConstants.SAVINGS);
		accounts.setBranchAddress(AccountsConstants.ADDRESS);
		Long randomNumber = 10000000000L + new Random(900000000).nextInt();
		System.out.println(randomNumber);
		accounts.setAccountNumber(randomNumber);
		return accounts;
	}

	@Override
	public CustomerDto fetchCustomerDetails(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString()));
		CustomerDto mapCustomerToCustomerDto = CustomerMapper.mapCustomerToCustomerDto(customer, new CustomerDto());
		AccountsDto accountsDto = AccountsMapper.toAccountsDto(accounts, new AccountsDto());
		mapCustomerToCustomerDto.setAccountsDto(accountsDto);
		return mapCustomerToCustomerDto;
	}

	@Override
	public boolean updateCustomerDetails(CustomerDto customerDto) {
		boolean isUpdated = false;
		
		 if(customerDto != null) {
			 Accounts accounts = accountsRepository.findById(customerDto.getAccountsDto().getAccountNumber())
					                                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", customerDto.getAccountsDto().getAccountNumber().toString()));
			 Accounts updatedAccounts = AccountsMapper.toAccounts(customerDto.getAccountsDto(), accounts);
			 accountsRepository.save(updatedAccounts);
			 
			 Customer oldCustomerObj = customerRepository.findById(updatedAccounts.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Customer", "CustomerId", updatedAccounts.getCustomerId().toString()));
			 Customer updatedCustomerObj = CustomerMapper.mapToCustomer(customerDto, oldCustomerObj);
			 Customer savedCustomer = customerRepository.save(updatedCustomerObj);
			 if(savedCustomer != null) {
				 isUpdated = true;
			 }
			 
			 
		 }
		return isUpdated;
	}

	@Override
	public boolean deleteCustomer(String mobileNumber) {
		if(!mobileNumber.isEmpty()) {
			System.out.println(mobileNumber);
			Customer customer = customerRepository.findByMobileNumber(mobileNumber.trim()).orElseThrow(()-> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
			System.out.println(customer);
			accountsRepository.deleteByCustomerId(customer.getCustomerId());
			customerRepository.deleteById(customer.getCustomerId());
			return true;
		}
		return false;
	}
	
}
