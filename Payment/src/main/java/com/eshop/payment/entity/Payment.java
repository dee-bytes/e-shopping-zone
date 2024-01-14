package com.eshop.payment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="payment")
public class Payment {
	@Id
	private String transactionId;
	private String cartId;
	private String fullName;
	private double amount;
	private String transactionStatus;
	private String paymentMode;
	
}
