package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/inventory")
public class InventoryController {

   private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Inventory>> addInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.createInventory(inventory);
        ApiResponse<Inventory> response = new ApiResponse<>(201, "Inventory added successfully", savedInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for ID: " + id));

        ApiResponse<Inventory> response = new ApiResponse<>(200, "Inventory found successfully", inventory);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory updated successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> patchInventory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Inventory updatedInventory = inventoryService.patchInventory(id, updates);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory patched successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(@PathVariable Long id) {
        boolean isDeleted = inventoryService.deleteInventory(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }
    }

    @GetMapping("/name/{vegetableName}")
    public ResponseEntity<ApiResponse<Inventory>> findByVegetableName(@PathVariable String vegetablename) {
            Inventory inventory = inventoryService.findInventoryByVegetableName(vegetablename);
            if(inventory!=null){
                ApiResponse<Inventory> response = new ApiResponse<>(200,"Inventory found",inventory);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else{
                ApiResponse<Inventory> response = new ApiResponse<>(404,"Inventory not found",null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
    }

    @DeleteMapping("name/{vegetableName}")
    public ResponseEntity<ApiResponse<Void>> deleteInventoryByVegetableName(@PathVariable String vegetableName) {
        boolean isDeleted = inventoryService.deleteInventoryByVegetableName(vegetableName);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Inventory item not found", null));
        }

    }


}
