package com.tyk.accounts.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
		super(String.format("%s resource is not found with given input data  %s:%s",resource,fieldName,fieldValue));

	}
}
