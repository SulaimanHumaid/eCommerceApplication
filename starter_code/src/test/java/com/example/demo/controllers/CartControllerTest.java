package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddToCartSuccess() {
        String username = "test user";
        int quantity = 2;
        User user = new User();
        user.setUsername(username);
        Cart cart = new Cart();
        user.setCart(cart);
        Item item = new Item(1L, "Test Item", BigDecimal.valueOf(10), "Test description");
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(1L);
        request.setQuantity(quantity);
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testRemoveFromCartSuccess() {
        String username = "test user";
        int quantity = 2;
        User user = new User();
        user.setUsername(username);
        Cart cart = new Cart();
        user.setCart(cart);
        Item item = new Item(1L, "Test Item", BigDecimal.valueOf(10), "Test description");
        cart.addItem(item);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(1L);
        request.setQuantity(quantity);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }
}