package com.eshop.CartService.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus statusmessage;
    private String message;
    private LocalDateTime datetime;
    
}
