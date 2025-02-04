package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.InventoryRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.OrderRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Seller seller;
    private Inventory inventory;
    private PurchaseOrder updatedOrder;
    private PurchaseOrder existingOrder;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setId(1L);
        seller.setName("Seller 1");
        seller.setSellerAddress("Btm 2nd stage");
        seller.setEmail("seller1@example.com");
        seller.setPhone("1234567890");

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setVegetableName("Tomato");
        inventory.setPricePerKg(100.0);
        inventory.setVegetableStockLeft(50);

        existingOrder = new PurchaseOrder();
        existingOrder.setId(1L);
        existingOrder.setSeller(seller);
        existingOrder.setPurchasedInventory(inventory);
        existingOrder.setQuantityPurchased(10);
        existingOrder.setBuyerName("Buyer 1");
        existingOrder.setBuyerPhone("0987654321");
        existingOrder.setShippingAddress("123 Street");
        existingOrder.setInvoiceNumber("INV12345");
        existingOrder.setOrderDate(LocalDateTime.now());

        // Initialize updated order
        updatedOrder = new PurchaseOrder();
        updatedOrder.setId(1L);
        updatedOrder.setQuantityPurchased(7); // Updated quantity
        updatedOrder.setSeller(seller);
        updatedOrder.setPurchasedInventory(inventory);
    }

    // Test case for createOrder
    @Test
    void createOrder_ShouldCreateOrder_WhenValid() {
        // Arrange
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(orderRepository.save(any(PurchaseOrder.class))).thenReturn(existingOrder);

        // Act
        PurchaseOrder createdOrder = orderService.createOrder(existingOrder);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(existingOrder.getTotalPrice(), createdOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(any(PurchaseOrder.class));
    }

    // Test case for createOrder with insufficient inventory
    @Test
    void createOrder_ShouldThrowException_WhenInsufficientInventory() {
        // Arrange
        existingOrder.setQuantityPurchased(60);  // Exceeding available stock
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(existingOrder);
        });
        assertEquals("Insufficient inventory quantity", exception.getMessage());
    }

    // Test case for getOrderById
    @Test
    void getOrderById_ShouldReturnOrder_WhenValidId() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        // Act
        PurchaseOrder fetchedOrder = orderService.getOrderById(1L);

        // Assert
        assertNotNull(fetchedOrder);
        assertEquals(existingOrder.getId(), fetchedOrder.getId());
    }

    // Test case for getOrderById when order is not found
    @Test
    void getOrderById_ShouldThrowException_WhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });
        assertEquals("Order not found", exception.getMessage());
    }

    // Test case for updateOrder
    @Test
    void updateOrder_ShouldUpdateOrder_WhenValid() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(orderRepository.save(any(PurchaseOrder.class))).thenReturn(existingOrder);

        // Act
        existingOrder.setQuantityPurchased(5);  // Update the quantity
        PurchaseOrder updatedOrder = orderService.updateOrder(1L, existingOrder);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(5, updatedOrder.getQuantityPurchased());
    }

    @Test
    public void testUpdateOrder_InsufficientInventory() {
        // Update the quantity to exceed available stock
        updatedOrder.setQuantityPurchased(110); // More than available stock

        // Mocking the behavior of repositories
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        // Call the method under test and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(1L, updatedOrder);
        });

        assertEquals("Insufficient inventory quantity", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_SellerNotFound() {
        // Mocking the behavior of repositories
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method under test and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(1L, updatedOrder);
        });

        assertEquals("Seller not found", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_InventoryNotFound() {
        // Mocking the behavior of repositories
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(1L, updatedOrder);
        });

        assertEquals("Inventory item not found" , exception.getMessage());

    }

    // Test case for deleteOrderById
    @Test
    void deleteOrderById_ShouldDeleteOrder_WhenValidId() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        // Act
        boolean isDeleted = orderService.deleteOrderById(1L);

        // Assert
        assertTrue(isDeleted);
        verify(orderRepository, times(1)).delete(existingOrder);
    }

    @Test
    void getOrderById_ShouldThrowRuntimeException_WhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });
        assertEquals("Order not found", exception.getMessage());
    }


    // Test case for patchOrder
    @Test
    void patchOrder_ShouldPatchOrder_WhenValid() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
//        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(orderRepository.save(any(PurchaseOrder.class))).thenReturn(existingOrder);

        // Act
        existingOrder.setQuantityPurchased(5);  // Update the quantity
        PurchaseOrder patchedOrder = orderService.patchOrder(1L, existingOrder);

        // Assert
        assertNotNull(patchedOrder);
        assertEquals(5, patchedOrder.getQuantityPurchased());
    }

}
