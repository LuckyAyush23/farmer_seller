package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void createOrder_ShouldReturnCreatedOrder_WhenValidRequest() {
        // Arrange
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);  // Mock the order details
        when(orderService.createOrder(any(PurchaseOrder.class))).thenReturn(purchaseOrder);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(200, "Order is created", purchaseOrder);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.createOrder(purchaseOrder);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createOrder_ShouldReturnBadRequest_WhenOrderCreationFails() {
        // Arrange
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        when(orderService.createOrder(any(PurchaseOrder.class))).thenThrow(new RuntimeException("Order creation failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            orderController.createOrder(purchaseOrder);
        });
    }

    @Test
    public void getOrderById_ShouldReturnOrder_WhenOrderExists() {
        // Arrange
        Long orderId = 1L;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(orderId);
        when(orderService.getOrderById(orderId)).thenReturn(purchaseOrder);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(200, "Order found with id " + orderId, purchaseOrder);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void getOrderById_ShouldReturnNotFound_WhenOrderDoesNotExist() {
        // Arrange
        Long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(null);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(404, "Order not found with id " + orderId, null);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateOrder_ShouldReturnUpdatedOrder_WhenValidOrderId() {
        // Arrange
        Long orderId = 1L;
        PurchaseOrder updatedOrder = new PurchaseOrder();
        updatedOrder.setId(orderId);
        updatedOrder.setBuyerName("Updated Name");

        PurchaseOrder existingOrder = new PurchaseOrder();
        existingOrder.setId(orderId);

        when(orderService.updateOrder(eq(orderId), any(PurchaseOrder.class))).thenReturn(updatedOrder);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(200, "order updated", updatedOrder);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.updateOrder(orderId, updatedOrder);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateOrder_ShouldReturnNotFound_WhenOrderIdNotExist() {
        // Arrange
        Long orderId = 1L;
        PurchaseOrder updatedOrder = new PurchaseOrder();
        updatedOrder.setId(orderId);

        when(orderService.updateOrder(eq(orderId), any(PurchaseOrder.class))).thenReturn(null);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(404, "order not updated as id is invalid", null);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.updateOrder(orderId, updatedOrder);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void patchOrder_ShouldReturnPatchedOrder_WhenValidOrderId() {
        // Arrange
        Long orderId = 1L;
        PurchaseOrder patchOrder = new PurchaseOrder();
        patchOrder.setId(orderId);
        patchOrder.setBuyerName("Patched Name");

        PurchaseOrder existingOrder = new PurchaseOrder();
        existingOrder.setId(orderId);

        when(orderService.patchOrder(eq(orderId), any(PurchaseOrder.class))).thenReturn(patchOrder);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(200, "Order patched successfully", patchOrder);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.patchOrder(orderId, patchOrder);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void patchOrder_ShouldReturnNotFound_WhenOrderIdNotExist() {
        // Arrange
        Long orderId = 1L;
        PurchaseOrder patchOrder = new PurchaseOrder();
        patchOrder.setId(orderId);

        when(orderService.patchOrder(eq(orderId), any(PurchaseOrder.class))).thenReturn(null);

        ApiResponse<PurchaseOrder> expectedResponse = new ApiResponse<>(404, "Order not found", null);

        // Act
        ResponseEntity<ApiResponse<PurchaseOrder>> response = orderController.patchOrder(orderId, patchOrder);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void deleteById_ShouldReturnSuccess_WhenOrderIsDeleted() {
        // Arrange
        Long orderId = 1L;
        when(orderService.deleteOrderById(orderId)).thenReturn(true);

        ApiResponse<Void> expectedResponse = new ApiResponse<>(200, "Order deleted successfully", null);

        // Act
        ResponseEntity<ApiResponse<Void>> response = orderController.deleteById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void deleteById_ShouldReturnNotFound_WhenOrderDoesNotExist() {
        // Arrange
        Long orderId = 1L;
        when(orderService.deleteOrderById(orderId)).thenReturn(false);

        ApiResponse<Void> expectedResponse = new ApiResponse<>(404, "Order not deleted successfully", null);

        // Act
        ResponseEntity<ApiResponse<Void>> response = orderController.deleteById(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}




