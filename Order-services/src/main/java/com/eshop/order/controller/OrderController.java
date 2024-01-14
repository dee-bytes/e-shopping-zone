package com.eshop.order.controller;

import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.order.entity.Address;
import com.eshop.order.entity.Cart;
import com.eshop.order.entity.Orders;
import com.eshop.order.exception.AddressNotFoundException;
import com.eshop.order.exception.OrdersNotFoundException;
import com.eshop.order.service.OrderService;


@RestController
@RequestMapping("/order")
@CrossOrigin("http://localhost:3000")
public class OrderController {
	
	private final Logger logger = LogManager.getLogger();
	
	@Autowired
	private OrderService orderservice;
	
	//Logger logger = LoggerFactory.getLogger(LoggerResponse.class);
	
	@GetMapping("/allOrders")
	public ResponseEntity<List<Orders>> getAllOrders(){
		logger.info("Sending request to the Order");
		List<Orders> o1 = orderservice.getAllOrders();
		logger.info("get all orders");
		return new ResponseEntity<>(o1,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/allAddress")
	public ResponseEntity<List<Address>> getAllAddress() throws AddressNotFoundException{
		logger.info("Sending request to the Order");
		List<Address> o1 = orderservice.getAllAddress();
		logger.info("get All address");
		return new ResponseEntity<>(o1,HttpStatus.CREATED);
	}
//	
//	@GetMapping("/getorderbyid/{orderId}")
//	public  ResponseEntity<Orders> getOrderByOrderId(@PathVariable String orderId) throws OrdersNotFoundException{
//		logger.info("Sending request to the Order");
//		Optional<Orders> o1 = orderservice.getOrderById(orderId);
//		logger.info("get orders by orderid");
//		return new ResponseEntity<>(o1,HttpStatus.CREATED);
//	}
	
//	@GetMapping("/getorderbyaddress/{id}")
//	public ResponseEntity<List<Address>> getOrderByCustomerId(@PathVariable Integer id) throws AddressNotFoundException {
//		logger.info("Sending request to the Order");
//		List<Address> o1 = orderserviceimpl.getOrderByCustomerId(id);
//		logger.info("get order by customerid");
//		return new ResponseEntity<>(o1,HttpStatus.CREATED);
//	}
	
	@GetMapping("/getOrderByCustomerId/{customerId}")
	public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable String customerId) throws OrdersNotFoundException{
		logger.info("Sending request to the Order");
		List<Orders> o1 = orderservice.getOrderByCustomerId(customerId);
		logger.info("get order by customer");
		return new ResponseEntity<>(o1,HttpStatus.CREATED);
	}
	@GetMapping("/getAddressByCustomerId/{customerId}")
	public ResponseEntity<List<Address>> getAddressByCustomerId(@PathVariable String customerId){
		List<Address> ad = orderservice.getAddressByCustomerId(customerId);
		logger.info("Get All Address by customerId");
		return new ResponseEntity<>(ad,HttpStatus.CREATED);
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<Void> placeOrders(@Valid @RequestBody Cart cart) throws OrdersNotFoundException {
		logger.info("Sending request to the Order");
		orderservice.placeOrder(cart);
		logger.info("order placed");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/storeAddress")
	public ResponseEntity<Void> storeAddress(@RequestBody Address address) {
		logger.info("Sending request to the Order");
		orderservice.storeAddress(address);
		logger.info("address stored");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/changeStatus/{status}/{id}")
	public ResponseEntity<Void> changeOrderStatus(@PathVariable String status,@PathVariable String orderId) {
		logger.info("Sending request to the Order");
		orderservice.changeStatus(status, orderId);
		logger.info("order status changed");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable String orderId)throws OrdersNotFoundException {
		logger.info("Sending request to the Order");
		orderservice.deleteOrder(orderId);
		logger.info("deleted");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
//	@DeleteMapping("/deleteAddress/{customerId}")
//	public ResponseEntity<String> deletedAddress(@PathVariable String customerId){
//		String s1 = orderServiceImpl.deleteAddress(customerId);
//		return new ResponseEntity<String>(s1,HttpStatus.OK);
//	}
}
