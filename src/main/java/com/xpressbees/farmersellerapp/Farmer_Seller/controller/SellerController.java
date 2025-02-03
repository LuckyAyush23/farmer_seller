package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/seller")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Seller>> createSeller(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Seller created successfully", createdSeller));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Seller>> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        if (seller != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller fetched successfully", seller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller not found", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Seller>> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.updateSeller(id, seller);
        if (updatedSeller != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller updated successfully", updatedSeller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller not found", null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Seller>> patchSeller(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Seller updatedSeller = sellerService.patchSeller(id, updates);
        if (updatedSeller != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller patched successfully", updatedSeller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeller(@PathVariable Long id) {
        boolean isDeleted = sellerService.deleteSeller(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller ID not found", null));
        }
    }
}
