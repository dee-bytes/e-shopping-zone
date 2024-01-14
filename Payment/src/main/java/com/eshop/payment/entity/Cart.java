package com.eshop.payment.entity;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	private String cartId;
	private List<Itemss> items;
	private double totalPrice;

}
