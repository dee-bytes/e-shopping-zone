package com.eshop.product.Entity;

//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {
	    @Id
	    private String productId;

	    @NotBlank(message = "Product type is required")
	    private String productType;

	    @NotBlank(message = "Product name is required")
	    private String productName;

	    @NotBlank(message = "Category is required")
	    private String category;

	    @Pattern(regexp = "[1-5]", message = "Rating should be between 1 and 5")
	    private String rating;

//	    @NotBlank(message = "Review is required")
	    private String review;

	    @NotBlank(message = "Image is required")
	    private String image;

	    @PositiveOrZero(message = "Price should be a positive value")
	    private double price;

	    @NotBlank(message = "Description is required")
	    private String description;

	    @NotBlank(message = "Specification is required")
	    private String specification;


	

}
