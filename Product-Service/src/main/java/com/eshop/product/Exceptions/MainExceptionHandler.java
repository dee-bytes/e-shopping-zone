package com.eshop.product.Exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(ProductNotFoundException ex,WebRequest request) {

		ErrorResponse err = new ErrorResponse(LocalDate.now(),ex.getMessage(),request.getDescription(false),"Bad Request");
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ProductCategoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(ProductCategoryNotFoundException ex,WebRequest request) {

		ErrorResponse err = new ErrorResponse(LocalDate.now(),ex.getMessage(),request.getDescription(false),"Bad Request");
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ProductTypeNotExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(ProductTypeNotExistsException ex,WebRequest request) {

		ErrorResponse err = new ErrorResponse(LocalDate.now(),ex.getMessage(),request.getDescription(false),"Bad Request");
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,

 

                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder details= new StringBuilder();
        for(FieldError error:ex.getBindingResult().getFieldErrors()) {
            details.append(error.getDefaultMessage()).append(".");
        }
        ErrorResponse response =new ErrorResponse(LocalDate.now(),details.toString(),request.getDescription(false),"Bad Request");
//        log.error("Validation fails "+details.toString());
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }
	

}
