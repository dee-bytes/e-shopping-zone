package com.eshop.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshop.order.entity.Orders;

@Repository
public interface OrderRepository extends MongoRepository<Orders, String>{


	List<Orders> findByCustomerId(String customerId);
	Orders findFirstByOrderByOrderIdDesc(Orders order);



	
	
}
