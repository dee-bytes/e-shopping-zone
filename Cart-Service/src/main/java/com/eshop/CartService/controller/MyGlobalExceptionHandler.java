package com.eshop.CartService.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eshop.CartService.entity.ErrorResponse;
import com.eshop.CartService.exception.CartNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MyGlobalExceptionHandler {

	//private static final Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);

    @ExceptionHandler(value = {CartNotFoundException.class})
    public ErrorResponse cartNotFoundException(CartNotFoundException cx) {

        log.error(cx.getMessage());
        ErrorResponse error = new ErrorResponse();
        error.setStatusmessage(HttpStatus.NOT_FOUND);
        error.setDatetime(LocalDateTime.now());
        error.setMessage(cx.getMessage());
        return error;
    }

    @ExceptionHandler(value ={HttpRequestMethodNotSupportedException.class})
    public ErrorResponse methodtNotSupport(HttpRequestMethodNotSupportedException ex) {

        log.error(ex.getMessage());
        ErrorResponse error=new ErrorResponse();
        error.setStatusmessage(HttpStatus.METHOD_NOT_ALLOWED);
        error.setDatetime(LocalDateTime.now());
        error.setMessage(ex.getMessage());
        return error;

    }

      @ExceptionHandler(value = {Exception.class}) 
      public ErrorResponse handleException(Exception ex) {

      log.error(ex.getMessage());
      ErrorResponse error=new ErrorResponse();
      error.setStatusmessage(HttpStatus.BAD_REQUEST);
      error.setDatetime(LocalDateTime.now());
      error.setMessage(ex.getMessage());
      return error;

      }

 

}

