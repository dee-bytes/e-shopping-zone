package com.eshop.order.entity;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Orders {

	@Id
	private String orderId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate orderDate;
	
	private String customerId;
	

	@NotNull
	private double amountPaid;

	@NotEmpty
	private String modeOfPayment;

	private String orderStatus;

	private int quantity;
	
//	@DBRef
	private Cart cart;

}
