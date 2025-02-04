package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/inventory")
@Tag(name = "Inventory API", description = "Operations related to vegetable inventory management")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @Operation(summary = "Create a new inventory item")
    public ResponseEntity<ApiResponse<Inventory>> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.createInventory(inventory);
        ApiResponse<Inventory> response = new ApiResponse<>(201, "Inventory added successfully", savedInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory by ID")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryById(@PathVariable Long id) {
        try {
            Inventory inventory = inventoryService.getInventoryById(id);
            ApiResponse<Inventory> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory found successfully", inventory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InventoryNotFoundException ex) {
            ApiResponse<Inventory> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get all inventory items")
    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllInventory() {
        try {
            List<Inventory> inventories = inventoryService.getAllInventories();
            return new ResponseEntity<>(new ApiResponse<>(200, "All inventory found", inventories), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiResponse<>(500, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an inventory item")
    public ResponseEntity<ApiResponse<Inventory>> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory updated successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch (partially update) an inventory item")
    public ResponseEntity<ApiResponse<Inventory>> patchInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.patchInventory(id, inventory);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory patched successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory item by ID")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(@PathVariable Long id) {
        boolean isDeleted = inventoryService.deleteInventory(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @GetMapping("/name/{vegetableName}")
    @Operation(summary = "Get inventory by vegetable name")
    public ResponseEntity<ApiResponse<Inventory>> findByVegetableName(@PathVariable String vegetableName) {
        Inventory inventory = inventoryService.findInventoryByVegetableName(vegetableName);
        if (inventory != null) {
            ApiResponse<Inventory> response = new ApiResponse<>(200, "Inventory found", inventory);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            ApiResponse<Inventory> response = new ApiResponse<>(404, "Inventory not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("name/{vegetableName}")
    @Operation(summary = "Delete inventory by vegetable name")
    public ResponseEntity<ApiResponse<Void>> deleteInventoryByVegetableName(@PathVariable String vegetableName) {
        boolean isDeleted = inventoryService.deleteInventoryByVegetableName(vegetableName);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }



}

