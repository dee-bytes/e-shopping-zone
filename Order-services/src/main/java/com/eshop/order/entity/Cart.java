package com.eshop.order.entity;



import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Document(collection = "cart")
public class Cart {
	
	@Id
	private String cartId;
	
	private String customerId;
	private double totalPrice;
	private List<Items> items;
	
	
	public Cart(String cartId, List<Items> itmes) {
		
	}
	
	

}
