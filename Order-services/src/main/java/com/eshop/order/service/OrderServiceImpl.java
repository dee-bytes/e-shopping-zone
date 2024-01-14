package com.eshop.order.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eshop.order.controller.PaymentInterface;
import com.eshop.order.entity.Address;
import com.eshop.order.entity.Cart;
import com.eshop.order.entity.Orders;
import com.eshop.order.exception.AddressNotFoundException;
import com.eshop.order.exception.OrdersNotFoundException;
import com.eshop.order.repository.AddressRepository;
import com.eshop.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PaymentInterface paymentInterface;

//	public List<Orders> getAllOrders() throws OrdersNotFoundException {
//		
//		return orderRepository.findAll();
//
//	}
//	@Override
//	public List<Address> getAllAddressByFullName(String fullName) throws AddressNotFoundException {
//		List<Address> addresses = addressRepository.findByFullName(fullName);
//		return addresses;
//	}
//	
//	@Override
//	public Orders getOrderByOrderId(String orderId) throws OrdersNotFoundException {
//		Optional<Orders> order = orderRepository.findById(orderId);
//	    if (order != null) {
//	        return order.get();
//	    } else {
//	        throw new OrdersNotFoundException(orderId + " Does Not Exist");
//	    }
//	}

//	public List<Address> getOrderByCustomerId(Integer id) throws AddressNotFoundException {
//		List<Address> findById= addressrepository.findByCustomerId(id);
//		if (!findById.isEmpty()) {
//			return addressrepository.findByCustomerId(id);
//		} else {
//			throw new AddressNotFoundException(id + " Does Not Exists");
//		}
//
//	}

//	public List<Orders> getOrdersByAddress(int id) throws OrdersNotFoundException {
//		List<Orders> findByCustomerId = orderrepository.findByCustomerId(id);
//		if (!findByCustomerId.isEmpty()) {
//			return orderrepository.findByCustomerId(id);
//		} else {
//			throw new OrdersNotFoundException(id + " Does Not Exists");
//		}
//	}

//	@Override
//	public Address storeAddress(Address address) {
//		Optional<Address> findByCustomerId = addressRepository.findById(address.getCustomerId());
//		List<Address> addresses = addressRepository.findAll();
//		if (findByCustomerId.isPresent() == false) {
//			return addressRepository.save(address);
//		} else {
//			throw new AddressNotFoundException(address.getFullName() + " Already exists");
//		}
//	}
//
//	@Override
//	public Orders changeOrderStatus(String orderStatus, String orderId) {
//		Optional<Orders> orderOptional = orderRepository.findById(orderId);
//
//	    if (orderOptional.isPresent()) {
//	        Orders order = orderOptional.get();
//	        order.setOrderStatus(orderStatus);
//	        return orderRepository.save(order);
//	    } else {
//	        throw new OrdersNotFoundException("Invalid Id status cannot be changed");
//	    }
//
//	}
//
//	@Override
//	public String deleteOrder(String orderId) throws OrdersNotFoundException {
//		Optional<Orders> findById = orderRepository.findById(orderId);
//		if (findById.isPresent()) {
//			orderRepository.deleteById(orderId);
//			return "Deleted Sucessfully";
//
//		} else {
//			throw new OrdersNotFoundException(orderId + " Already been deleted or not been present");
//		}
//
//	}
//
//	@Override
//	public Orders placeorders(Orders order, String cartId) {
//		Optional<Orders> findById = orderRepository.findById(order.getOrderId());
//		ResponseEntity<Cart> cart = paymentInterface.getCartById(cartId);
//		if (findById.isEmpty()) {
//			if(cart.getBody() != null) {
////				order.setOrderId(orderRepository.findAll().size()+1);
//				order.setCart(cart.getBody());
////				order.setCustomerName(order .getAddress().getFullName());
//				order.setAmountPaid(cart.getBody().getTotalPrice());
//				order.setOrderStatus("order placed");
//				order.setOrderDate(LocalDate.now());
////				paymentInterface.deleteCart(cartId);
//				return orderRepository.insert(order);
//			}else {
//				throw new OrdersNotFoundException("Invalid CartId");
//			}
//			
//		} else {
//			throw new OrdersNotFoundException("Order is already placed on this id");
//		}
//	}
////	
//	public List<Orders> getAllOrdersByCustomerId(String customerId){
//		return orderRepository.findByCustomerId(customerId);
//	}
//	
//	@Override
//	public List<Address> getAllAddress(){
//		return addressRepository.findAll();
//	}
//	
//	
//	public String deleteAddress(String customerId) {
//		Address address = addressRepository.findCustomerById(customerId);
//		if(address != null) {
//			addressRepository.deleteById(customerId);
//			return "Address Deleted";
//		}
//		else {
//			throw new OrdersNotFoundException("address not found");
//		}
//		
//	}
	
	public void placeOrder(Cart cart) {
		// Retrieve cart details using the PaymentInterface
	    ResponseEntity<Cart> cartResponse = paymentInterface.getCartById(cart.getCartId());

	    if (cartResponse.getStatusCode() == HttpStatus.OK) {
	        Cart retrievedCart = cartResponse.getBody();

	        if (retrievedCart != null) {
	            // Set the customerId in the order based on the retrieved cart
	            Orders order = new Orders();
	            order.setCart(retrievedCart);
	            // Set other order details
	            order.setOrderDate(LocalDate.now());
	            order.setCustomerId(retrievedCart.getCustomerId()); // Set the customerId from the cart
	            order.setAmountPaid(retrievedCart.getTotalPrice());
	            order.setOrderStatus("Order Placed");
	            order.setQuantity(retrievedCart.getItems().size());

	            // Save the order to the repository
	            orderRepository.save(order);

	            // Delete the cart using the PaymentInterface
	            paymentInterface.deleteCart(cart.getCartId());
	        } else {
	            throw new OrdersNotFoundException("Cart not found for cartId: " + cart.getCartId());
	        }
	    } else {
	        throw new OrdersNotFoundException("Invalid Cart ID: " + cart.getCartId());
	    }
		
	}
	@Override
	public String changeStatus(String status, String orderId) {
		Orders order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new OrdersNotFoundException("Order with ID " + orderId + " not found.");
        }

        order.setOrderStatus(status);
        orderRepository.save(order);

        return "Order status updated successfully.";
		
	}
	@Override
	public List<Orders> getOrderByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return orderRepository.findByCustomerId(customerId);	}
	@Override
	public List<Address> getAddressByCustomerId(String customerId) {
		 return addressRepository.findByCustomerId(customerId);
	}
	@Override
	public Optional<Orders> getOrderById(String orderId) {
		return Optional.ofNullable(orderRepository.findById(orderId).orElse(null));
	}

	@Override
	public void onlinePayment(Cart cart) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Orders> getAllOrders() {
		return orderRepository.findAll();
	}
	@Override
	public void deleteOrder(String orderId) {
		if (!orderRepository.existsById(orderId)) {
            throw new OrdersNotFoundException("Order with ID " + orderId + " not found.");
        }

        orderRepository.deleteById(orderId);
		
	}
	@Override
	public void storeAddress(Address address) {
		if (addressRepository.existsById(address.getCustomerId())) {
            throw new AddressNotFoundException("Address with customer ID " + address.getCustomerId() + " already exists.");
        }

        addressRepository.save(address);
		
	}
	@Override
	public List<Address> getAllAddress() {
		return addressRepository.findAll();
	}
	
	


}
