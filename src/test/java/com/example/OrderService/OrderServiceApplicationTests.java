package com.example.OrderService;

import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.Product;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceApplicationTests {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllOrders() {
		// Given
		List<Order> orderList = new ArrayList<>();
		Order order1=new Order();
		order1.setId(1);
		order1.setQuantity(2);
		order1.setTotal_price(45.78);
		order1.setProduct_id(2);

		Order order2=new Order();
		order1.setId(2);
		order1.setQuantity(3);
		order1.setTotal_price(50.00);
		order1.setProduct_id(3);

		orderList.add(order1);
		orderList.add(order2);

		when(orderRepository.findAll()).thenReturn(orderList);
		assertEquals(orderService.getAllOrders().size(),2);
		assertNotNull(orderService.getAllOrders());
	}

	@Test
	public void testCreateOrder() {
		// Given
		Order order1=new Order();
		order1.setId(1);
		order1.setQuantity(2);
		order1.setTotal_price(20.00);
		order1.setProduct_id(2);

		Product product = new Product();
		product.setId(2);
		product.setName("Product Name");
		product.setPrice(10.0);
		product.setDescription("This is Beautiful Product");

		ResponseEntity<Product> productResponse = ResponseEntity.ok(product);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(Product.class)))
				.thenReturn(productResponse);
		when(orderRepository.save(any(Order.class))).thenReturn(order1);

		// When
		Order result = orderService.createOrder(order1);

		// Then
		assertNotNull(result);
		assertEquals(order1.getProduct_id(), result.getProduct_id());
		assertEquals(order1.getQuantity(), result.getQuantity());
		assertEquals(20.0, result.getTotal_price()); // 2 * 10.0
		verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(Product.class));
		verify(orderRepository, times(1)).save(order1);
	}

	@Test
	public void testCreateOrder_ProductNotFound() {
		// Given
		Order order1=new Order();
		order1.setId(1);
		order1.setQuantity(2);
		order1.setTotal_price(45.78);
		order1.setProduct_id(2);
		ResponseEntity<Product> productResponse = ResponseEntity.ok(null); // Simulating product not found scenario

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(Product.class)))
				.thenReturn(productResponse);

		// When
		Exception exception = assertThrows(NullPointerException.class, () -> {
			orderService.createOrder(order1);
		});

		// Then
		assertNotNull(exception);
		verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(Product.class));
		verify(orderRepository, never()).save(any(Order.class)); // Ensure save is not called
	}

}
