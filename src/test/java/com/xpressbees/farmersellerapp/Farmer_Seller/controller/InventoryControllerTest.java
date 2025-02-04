package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setVegetableName("Tomato");
        inventory.setVegetableStockLeft(100);
        inventory.setPricePerKg(50.0);
    }

    @Test
    void testCreateInventory_Success() {
        when(inventoryService.createInventory(any(Inventory.class))).thenReturn(inventory);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.createInventory(inventory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Inventory added successfully", response.getBody().getMessage());
        assertEquals(inventory, response.getBody().getData());
    }

    @Test
    void testGetInventoryById_Success() {
        when(inventoryService.getInventoryById(1L)).thenReturn(inventory);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.getInventoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory found successfully", response.getBody().getMessage());
        assertEquals(inventory, response.getBody().getData());
    }

    @Test
    void testGetInventoryById_NotFound() {
        when(inventoryService.getInventoryById(1L)).thenThrow(new InventoryNotFoundException("Inventory not found for ID: 1"));

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.getInventoryById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory not found for ID: 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testGetAllInventory_Success() {
        when(inventoryService.getAllInventories()).thenReturn(Collections.singletonList(inventory));

        ResponseEntity<ApiResponse<List<Inventory>>> response = inventoryController.getAllInventory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All inventory found", response.getBody().getMessage());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(inventory, response.getBody().getData().get(0));
    }

    @Test
    void testGetAllInventory_EmptyList() {
        when(inventoryService.getAllInventories()).thenThrow(new RuntimeException("No inventory found"));

        ResponseEntity<ApiResponse<List<Inventory>>> response = inventoryController.getAllInventory();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No inventory found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testUpdateInventory_Success() {
        when(inventoryService.updateInventory(eq(1L), any(Inventory.class))).thenReturn(inventory);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.updateInventory(1L, inventory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory updated successfully", response.getBody().getMessage());
        assertEquals(inventory, response.getBody().getData());
    }

    @Test
    void testUpdateInventory_NotFound() {
        when(inventoryService.updateInventory(eq(1L), any(Inventory.class))).thenReturn(null);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.updateInventory(1L, inventory);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory item not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteInventory_Success() {
        when(inventoryService.deleteInventory(1L)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = inventoryController.deleteInventory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory item deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteInventory_NotFound() {
        when(inventoryService.deleteInventory(1L)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = inventoryController.deleteInventory(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory item not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testFindByVegetableName_Success() {
        when(inventoryService.findInventoryByVegetableName("Tomato")).thenReturn(inventory);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.findByVegetableName("Tomato");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory found", response.getBody().getMessage());
        assertEquals(inventory, response.getBody().getData());
    }

    @Test
    void testFindByVegetableName_NotFound() {
        when(inventoryService.findInventoryByVegetableName("Tomato")).thenReturn(null);

        ResponseEntity<ApiResponse<Inventory>> response = inventoryController.findByVegetableName("Tomato");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteInventoryByVegetableName_Success() {
        when(inventoryService.deleteInventoryByVegetableName("Tomato")).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = inventoryController.deleteInventoryByVegetableName("Tomato");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory item deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteInventoryByVegetableName_NotFound() {
        when(inventoryService.deleteInventoryByVegetableName("Tomato")).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = inventoryController.deleteInventoryByVegetableName("Tomato");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory item not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
