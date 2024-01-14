package com.eshop.payment.service;

import java.util.List;

import com.eshop.payment.entity.Payment;
import com.eshop.payment.entity.PaymentType;
import com.eshop.payment.exception.PaymentFailException;
import com.eshop.payment.exception.PaymentNotFoundWithIdException;


public interface PaymentService {
	
	Payment  doPayment(String cartId,Payment payment) throws PaymentFailException;
	Payment getPayment(String transactionId) throws PaymentNotFoundWithIdException;
	//Payment updatePayment(int transactionId,Payment payment) throws PaymentNotFoundWithIdException;
	
	List<Payment> getAllPayments();

}
