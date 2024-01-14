package com.eshop.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.payment.entity.Payment;
import com.eshop.payment.exception.PaymentFailException;
import com.eshop.payment.exception.PaymentNotFoundWithIdException;
import com.eshop.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin("http://localhost:3000")
public class PaymentController {

	@Autowired
	private PaymentService service;

	@PostMapping("/doPayment/{cartId}")
	public Payment doPayment(@PathVariable String cartId,@RequestBody Payment payment)
			throws PaymentFailException {
		return service.doPayment(cartId, payment);
	}

	@GetMapping("/getByTid/{transactionId}")
	public ResponseEntity<Payment> getPayment(@PathVariable String transactionId) throws PaymentNotFoundWithIdException {
		Payment payment = service.getPayment(transactionId);
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@GetMapping("/getAllPayments")
	public ResponseEntity<List<Payment>> getPayment() {
		List<Payment> payment = service.getAllPayments();
		return new ResponseEntity<List<Payment>>(payment, HttpStatus.OK);
	}
	
}
