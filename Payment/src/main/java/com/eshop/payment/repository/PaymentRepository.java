package com.eshop.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eshop.payment.entity.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

	Payment findByTransactionId(String transactionId);

	boolean existsByTransactionId(String transactionId);

}
