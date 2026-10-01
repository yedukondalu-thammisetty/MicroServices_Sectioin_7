package com.tyk.loans.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
		
		super(String.format("%s resource is not for this %s:%s",resource,fieldName,fieldValue));
	}

}
