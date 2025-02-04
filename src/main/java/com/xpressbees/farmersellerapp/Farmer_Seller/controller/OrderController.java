package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponses;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponses<PurchaseOrder>> createOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder createdOrder = orderService.createOrder(purchaseOrder);
        ApiResponses<PurchaseOrder> response = new ApiResponses<>(200,"Order is created",createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponses<PurchaseOrder>> getOrderById(@PathVariable Long id){
        PurchaseOrder purchaseOrder = orderService.getOrderById(id);
        if(purchaseOrder!=null){
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(200,"Order found with id "+id,purchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(404,"Order not found with id "+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponses<PurchaseOrder>> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder){
        PurchaseOrder updatedPurchaseOrder = orderService.updateOrder(id,purchaseOrder);
        if(updatedPurchaseOrder!=null){
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(200,"order updated",updatedPurchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(404,"order not updated as id is invalid",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponses<PurchaseOrder>> patchOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder existingPurchaseOrder = orderService.patchOrder(id,purchaseOrder);
        if(existingPurchaseOrder!=null){
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(200,"Order patched successfully",existingPurchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponses<PurchaseOrder> response = new ApiResponses<>(404,"Order not found",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponses<Void>> deleteById(@PathVariable Long id){
        boolean isDeleted = orderService.deleteOrderById(id);
        if(isDeleted){
            ApiResponses<Void> response = new ApiResponses<>(200,"Order deleted successfully",null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            ApiResponses<Void> response = new ApiResponses<>(404,"Order not deleted successfully",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
