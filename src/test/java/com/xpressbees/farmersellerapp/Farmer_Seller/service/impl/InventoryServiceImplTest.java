package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.InventoryRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.setVegetableName("Tomato");
        inventory.setVegetableStockLeft(50);
        inventory.setPricePerKg(Double.valueOf(30));
    }

    @Test
    void testCreateInventory_Success() {
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        Inventory savedInventory = inventoryService.createInventory(inventory);
        assertNotNull(savedInventory);
        assertEquals("Tomato", savedInventory.getVegetableName());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void testCreateInventory_Failure_DuplicateVegetableName() {
        when(inventoryRepository.save(inventory)).thenThrow(new RuntimeException("Duplicate vegetable name , vegetable is already present"));
        Exception exception = assertThrows(RuntimeException.class, () -> inventoryService.createInventory(inventory));

        assertEquals("Duplicate vegetable name , vegetable is already present", exception.getMessage());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void testGetAllInventories_Success() {
        List<Inventory> inventories = Arrays.asList(inventory);
        when(inventoryRepository.findAll()).thenReturn(inventories);

        List<Inventory> result = inventoryService.getAllInventories();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetInventoryById_Success() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        Inventory foundInventory = inventoryService.getInventoryById(1L);
        assertNotNull(foundInventory);
        assertEquals("Tomato", foundInventory.getVegetableName());
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetInventoryById_Failure_NotFound() {
        when(inventoryRepository.findById(2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoryById(2L));
        assertEquals("Inventory not found for ID: 2", exception.getMessage());
        verify(inventoryRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateInventory_Success() {
        Inventory updatedInventory = new Inventory();
        updatedInventory.setVegetableName("Potato");
        updatedInventory.setVegetableStockLeft(200);
        updatedInventory.setPricePerKg(30.0);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(updatedInventory);

        Inventory result = inventoryService.updateInventory(1L, updatedInventory);
        assertNotNull(result);
        assertEquals("Potato", result.getVegetableName());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory_Failure_NotFound() {
        Inventory updatedInventory = new Inventory();
        when(inventoryRepository.findById(2L)).thenReturn(Optional.empty());
        Inventory result = inventoryService.updateInventory(2L, updatedInventory);
        assertNull(result);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testPatchInventory_Success() {
        Inventory patchData = new Inventory();
        patchData.setVegetableStockLeft(120);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.patchInventory(1L, patchData);
        assertNotNull(result);
        assertEquals(120, result.getVegetableStockLeft());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testPatchInventory_Failure_NotFound() {
        Inventory patchData = new Inventory();
        when(inventoryRepository.findById(2L)).thenReturn(Optional.empty());
        Inventory result = inventoryService.patchInventory(2L, patchData);
        assertNull(result);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testDeleteInventory_Success() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        boolean result = inventoryService.deleteInventory(1L);
        assertTrue(result);
        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInventory_Failure_NotFound() {
        when(inventoryRepository.findById(2L)).thenReturn(Optional.empty());
        boolean result = inventoryService.deleteInventory(2L);
        assertFalse(result);
        verify(inventoryRepository, never()).deleteById(2L);
    }

    @Test
    void testDeleteInventoryByVegetableName_Success() {
        when(inventoryRepository.findInventoryByVegetableName("Tomato")).thenReturn(inventory);
        boolean result = inventoryService.deleteInventoryByVegetableName("Tomato");
        assertTrue(result);
        verify(inventoryRepository, times(1)).delete(inventory);
    }

    @Test
    void testDeleteInventoryByVegetableName_Failure_NotFound() {
        when(inventoryRepository.findInventoryByVegetableName("Carrot")).thenReturn(null);
        boolean result = inventoryService.deleteInventoryByVegetableName("Carrot");
        assertFalse(result);
        verify(inventoryRepository, never()).delete(any(Inventory.class));
    }

    @Test
    void testFindInventoryByVegetableName_Success() {
        when(inventoryRepository.findInventoryByVegetableName("Tomato")).thenReturn(inventory);
        Inventory result = inventoryService.findInventoryByVegetableName("Tomato");
        assertNotNull(result);
        assertEquals("Tomato", result.getVegetableName());
        verify(inventoryRepository, times(1)).findInventoryByVegetableName("Tomato");
    }

    @Test
    void testFindInventoryByVegetableName_Failure_NotFound() {
        when(inventoryRepository.findInventoryByVegetableName("Carrot")).thenReturn(null);
        Inventory result = inventoryService.findInventoryByVegetableName("Carrot");
        assertNull(result);
        verify(inventoryRepository, times(1)).findInventoryByVegetableName("Carrot");
    }

}