package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponses;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponses<Inventory>> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.createInventory(inventory);
        ApiResponses<Inventory> response = new ApiResponses<>(201, "Inventory added successfully", savedInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponses<Inventory>> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for ID: " + id));

        ApiResponses<Inventory> response = new ApiResponses<>(200, "Inventory found successfully", inventory);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponses<List<Inventory>>> getAllInventory(){
        List<Inventory> inventoryList = inventoryService.getAllInventories();
        if (inventoryList.isEmpty()){
            throw new RuntimeException("No inventory found");
        }
        else{
            ApiResponses<List<Inventory>> response = new ApiResponses<>(200,"All inventory found",inventoryList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponses<Inventory>> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Inventory updated successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponses<>(404, "Inventory item not found", null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponses<Inventory>> patchInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.patchInventory(id, inventory);

        if (updatedInventory != null) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Inventory patched successfully", updatedInventory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponses<>(404, "Inventory item not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponses<Void>> deleteInventory(@PathVariable Long id) {
        boolean isDeleted = inventoryService.deleteInventory(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponses<>(404, "Inventory item not found", null));
        }
    }

    @GetMapping("/name/{vegetableName}")
    public ResponseEntity<ApiResponses<Inventory>> findByVegetableName(@PathVariable String vegetableName) {
        Inventory inventory = inventoryService.findInventoryByVegetableName(vegetableName);
        if (inventory != null) {
            ApiResponses<Inventory> response = new ApiResponses<>(200, "Inventory found", inventory);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            ApiResponses<Inventory> response = new ApiResponses<>(404, "Inventory not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("name/{vegetableName}")
    public ResponseEntity<ApiResponses<Void>> deleteInventoryByVegetableName(@PathVariable String vegetableName) {
        boolean isDeleted = inventoryService.deleteInventoryByVegetableName(vegetableName);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Inventory item deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponses<>(404, "Inventory item not found", null));
        }
    }


}

