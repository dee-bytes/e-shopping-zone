package com.eshop.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshop.order.entity.Address;
import com.eshop.order.entity.Orders;

@Repository
public interface AddressRepository extends MongoRepository<Address, String>{



	public List<Address> findByCustomerId(String customerId);



}
