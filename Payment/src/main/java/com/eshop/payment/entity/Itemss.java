package com.eshop.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Itemss {
	private String productId;

	private String productName;

	private double price;

	private int quantity;
	private String image;

}
