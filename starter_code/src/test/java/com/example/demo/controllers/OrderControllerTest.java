package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void TestSubmit() {
        User user = new User();
        user.setUsername("testuser");
        Cart cart = new Cart();
        cart.setId(1L);
        List<Item> items = new ArrayList<>();
        items.add(new Item(1L, "testitem", BigDecimal.valueOf(1000), "TestDescription"));
        cart.setItems(items);
        user.setCart(cart);
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        UserOrder order = UserOrder.createFromCart(cart);
        when(orderRepository.save(order)).thenReturn(order);
        ResponseEntity<UserOrder> response = orderController.submit("testuser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }


    @Test
    public void TestGetOrdersForUser() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        List<UserOrder> orders = new ArrayList<>();
        UserOrder order1 = new UserOrder();
        order1.setId(1L);
        orders.add(order1);
        UserOrder order2 = new UserOrder();
        order2.setId(2L);
        orders.add(order2);
        when(orderRepository.findByUser(user)).thenReturn(orders);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testuser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }
}