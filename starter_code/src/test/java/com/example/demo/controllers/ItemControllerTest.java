package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItems() {

        Item item = new Item(1L, "Test Item1", BigDecimal.valueOf(10), "Test description1");
        itemRepository.save(item);
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testGetItemById() {

        Item item = new Item(2L, "Test Item2", BigDecimal.valueOf(20), "Test description2");
        itemRepository.save(item);
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);

    }

    @Test
    public void testGetItemsByName() {
        Item item = new Item(2L, "Test Item", BigDecimal.valueOf(20), "Test description2");
        itemRepository.save(item);
        when(itemRepository.findByName("Test Item")).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Test Item");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }
}