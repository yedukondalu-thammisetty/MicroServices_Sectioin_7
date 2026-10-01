package com.tyk.cards.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String resource, String field, String fieldValue) {
		super(String.format("%s resource is not found with this %s:%s", resource,field, fieldValue));
	}

}
