package com.tyk.accounts.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tyk.accounts.dto.ErrorResponseDto;
import com.tyk.accounts.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected  ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
             HashMap<String,String> hashMap = new HashMap<>();
             List<ObjectError> allErrors = ex.getAllErrors();
             allErrors.forEach(error->{
            	 String field = ((FieldError) error).getField();
            	 String message = error.getDefaultMessage();
            	 hashMap.put(field, message);
             });
		return new ResponseEntity<Object>(hashMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalExceptions(Exception exception,WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage(), LocalDateTime.now());
        return  new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExist(CustomerAlreadyExistException customerAlreadyExistException, WebRequest webRequest) {
         ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,customerAlreadyExistException.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExist(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
         ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,resourceNotFoundException.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }
    
}
