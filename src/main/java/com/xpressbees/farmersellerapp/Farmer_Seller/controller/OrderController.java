package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@Tag(name = "OrderApi API", description = "Operations related to order purchased")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Creates a new purchase order and returns the created order details."
    )
    public ResponseEntity<ApiResponse<PurchaseOrder>> createOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder createdOrder = orderService.createOrder(purchaseOrder);
        ApiResponse<PurchaseOrder> response = new ApiResponse<>(200,"Order is created",createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Fetches details of a specific order using its ID."
    )
    public ResponseEntity<ApiResponse<PurchaseOrder>> getOrderById(@PathVariable Long id){
        PurchaseOrder purchaseOrder = orderService.getOrderById(id);
        if(purchaseOrder!=null){
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(200,"Order found with id "+id,purchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(404,"Order not found with id "+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an order",
            description = "Updates an existing purchase order based on the provided ID."
    )
    public ResponseEntity<ApiResponse<PurchaseOrder>> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder){
        PurchaseOrder updatedPurchaseOrder = orderService.updateOrder(id,purchaseOrder);
        if(updatedPurchaseOrder!=null){
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(200,"order updated",updatedPurchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(404,"order not updated as id is invalid",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially update an order",
            description = "Updates specific fields of an order without modifying the entire record."
    )
    public ResponseEntity<ApiResponse<PurchaseOrder>> patchOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder existingPurchaseOrder = orderService.patchOrder(id,purchaseOrder);
        if(existingPurchaseOrder!=null){
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(200,"Order patched successfully",existingPurchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponse<PurchaseOrder> response = new ApiResponse<>(404,"Order not found",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an order",
            description = "Removes an order from the system using its ID."
    )
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id){
        boolean isDeleted = orderService.deleteOrderById(id);
        if(isDeleted){
            ApiResponse<Void> response = new ApiResponse<>(200,"Order deleted successfully",null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            ApiResponse<Void> response = new ApiResponse<>(404,"Order not deleted successfully",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
