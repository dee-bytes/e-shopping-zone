package com.eshop.payment.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eshop.payment.controller.PaymentInterface;
import com.eshop.payment.entity.Cart;
import com.eshop.payment.entity.Payment;
import com.eshop.payment.exception.PaymentFailException;
import com.eshop.payment.exception.PaymentNotFoundWithIdException;
import com.eshop.payment.repository.PaymentRepository;

@Service
public class PaymentServiceImp implements PaymentService {

	@Autowired
	private PaymentRepository repository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private PaymentInterface paymentInterface;
	@Override
	public Payment doPayment(String cartId,Payment payment) throws PaymentFailException {
		boolean paymentDone = false;
		//ResponseEntity<Cart> res = restTemplate.getForEntity("http://localhost:3333/cart/" + cartId, Cart.class);
		ResponseEntity<Cart> res = paymentInterface.getCartById(cartId);
		
		Cart cart = res.getBody();
		if (cart.getCartId() == null) {
			throw new PaymentFailException("Invalid cart id");
		} else {
			String cid = cart.getCartId();
			double price = cart.getTotalPrice();
			try {
				Payment payment1v = new Payment();
				Random random = new Random();

				// Generate a random number between 100 and 1000 (inclusive)
				int randomNumber = random.nextInt(901) + 100;
				System.out.println(randomNumber);
				String tractionId = "" + cid + randomNumber;
				String id = tractionId;
				payment.setTransactionId(id);
				payment.setCartId(cid);
				payment.setFullName(payment.getFullName());
				payment.setAmount(price);
				payment.setTransactionStatus("Payment Successful");
				payment.setPaymentMode(payment.getPaymentMode());
				paymentDone = true;
				Payment save = repository.save(payment);
				paymentInterface.deleteCart(cartId);
				return save;

			} catch (Exception e) {
				// TODO: handle exception

				throw new PaymentFailException("Payment Failed of RS " + price);

			}
		}

	}

	@Override
	public Payment getPayment(String transactionId) throws PaymentNotFoundWithIdException {
		Payment pay = repository.findByTransactionId(transactionId);
		if(pay!= null) {
		//Payment payment= repository.findByTransactionId(transactionId);
		return pay;
		}else {
			throw new PaymentNotFoundWithIdException("Payment not found with transactionId "+transactionId);
		}
	}
	
	public List<Payment> getAllPayments(){
		return repository.findAll();
	}
	
	
//
//	@Override
//	public Payment updatePayment(int transactionId, Payment payment) throws PaymentNotFoundWithIdException {
//		// TODO Auto-generated method stub
//		if(repository.existsByTransactionId(transactionId)) {
//			repository.save(payment);
//			return payment;
//			}else {
//				throw new PaymentNotFoundWithIdException("Payment not found with transactionId "+transactionId);
//			}
////		repository.save(payment);
////		return payment;

}
