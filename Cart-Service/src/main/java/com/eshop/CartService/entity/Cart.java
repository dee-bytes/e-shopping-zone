package com.eshop.CartService.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class Cart {
	
//	@NotNull(message = "Cart ID cannot be null")
//    @Min(value = 1, message = "Cart ID must be a positive integer")
	@Id
    private String cartId;

    @NotEmpty(message = "Items list cannot be empty")
    @Valid // Validates the items in the list
    private List<Items> items;

    @Min(value = 0, message = "Total price cannot be negative")
    private double totalPrice;

}
