package com.eshop.order.service;

import java.util.List;
import java.util.Optional;

import com.eshop.order.entity.Address;
import com.eshop.order.entity.Cart;
import com.eshop.order.entity.Orders;


public interface OrderService {

	public List<Orders> getAllOrders();
	
	public void placeOrder(Cart cart);
	
	public String changeStatus(String status, String orderId);
	
	public void deleteOrder(String orderId);
	
	public List<Orders> getOrderByCustomerId(String customerId);
	
	public void storeAddress(Address address);
	
	public List<Address> getAddressByCustomerId(String customerId);
	
	public List<Address> getAllAddress();
	
	public Optional<Orders> getOrderById(String orderId);

	public void onlinePayment(Cart cart);

}
