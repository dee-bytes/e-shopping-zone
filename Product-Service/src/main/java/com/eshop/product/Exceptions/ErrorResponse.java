package com.eshop.product.Exceptions;

import java.time.LocalDate;



public class ErrorResponse {

    private LocalDate timestamp;

    private String message;

    private String details;

    private String httpCodeMessage;

    public LocalDate getTimestamp() {

        return timestamp;

    }

    public void setTimestamp(LocalDate timestamp) {

        this.timestamp = timestamp;

    }

    public String getMessage() {

        return message;

    }

    public void setMessage(String message) {

        this.message = message;

    }

    public String getDetails() {

        return details;

    }

    public void setDetails(String details) {

        this.details = details;

    }

    public String getHttpCodeMessage() {

        return httpCodeMessage;

    }

    public void setHttpCodeMessage(String httpCodeMessage) {

        this.httpCodeMessage = httpCodeMessage;

    }

    public ErrorResponse(LocalDate timestamp, String message, String details, String httpCodeMessage) {

        super();

        this.timestamp = timestamp;

        this.message = message;

        this.details = details;

        this.httpCodeMessage = httpCodeMessage;

    }

 

    public ErrorResponse() {

 

    }

}