package com.eshop.CartService.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
	private String productId;

	private String productName;
	@NotEmpty
	@Min(0)
	private double price;
	@Min(1)
	private int quantity;
	private String image;

}
