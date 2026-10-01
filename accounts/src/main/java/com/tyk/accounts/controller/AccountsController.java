package com.tyk.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyk.accounts.constants.AccountsConstants;
import com.tyk.accounts.dto.AccountsContactInfoDto;
import com.tyk.accounts.dto.CustomerDto;
import com.tyk.accounts.dto.ResponseDto;
import com.tyk.accounts.service.IAccountsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private IAccountsService accountsService;

    @Value("${build.version}")
    private String buildeVersion;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private AccountsContactInfoDto  accountsContactInfoDto;
   
    public AccountsController( IAccountsService accountsService) {
		this.accountsService = accountsService;
	}

    @GetMapping("say-hello")
    public String getMessage(){
        return "HI Yedukondalu, Your Implemented the Hello World Get Rest API";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount( @Valid @RequestBody CustomerDto customerDto){

        accountsService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.STATUS_201));
    }
    
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number  must be 10 digits")String mobileNumber){
    	
    	CustomerDto fetchCustomerDetails = accountsService.fetchCustomerDetails(mobileNumber);
    	return ResponseEntity.status(HttpStatus.OK).body(fetchCustomerDetails);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerDetails( @Valid@RequestBody CustomerDto customerDto){
    	boolean updateCustomerDetails = accountsService.updateCustomerDetails(customerDto);
    	if(updateCustomerDetails) {
    		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.MESSAGE_200, AccountsConstants.MESSAGE_200));
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    	}
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber){
    	boolean isCustomerDeleted = accountsService.deleteCustomer(mobileNumber);
    	if(isCustomerDeleted) {
    		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.MESSAGE_200, AccountsConstants.MESSAGE_200));
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    	}
    }

    @GetMapping("/build-version")
    public ResponseEntity<?> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildeVersion);
    }
    @GetMapping("/java-version")
    public ResponseEntity<?> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }
    
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
    	return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }
}
