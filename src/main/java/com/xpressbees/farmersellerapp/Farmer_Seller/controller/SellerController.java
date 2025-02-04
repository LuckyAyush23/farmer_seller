package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/seller")
@Tag(name = "Seller API", description = "Operations related to seller management")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }

    @PostMapping
    @Operation(summary = "Create a new seller", description = "Adds a new seller to the system , email and phone should be unique")
    public ResponseEntity<ApiResponse<Seller>> createSeller(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        if(createdSeller!=null){
            return ResponseEntity.ok(new ApiResponse<>(201, "Seller created successfully", seller));
        }
        else{
            throw new RuntimeException("Seller email or phone cannot be duplicate");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get seller by ID", description = "Fetch seller details by seller ID.")
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
    @Operation(summary = "Update seller details", description = "Updates seller information based on seller ID.")
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
    @Operation(
            summary = "Partially update seller details",
            description = "Allows partial updates to seller details using the seller ID."
    )
    public ResponseEntity<ApiResponse<Seller>> patchSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.patchSeller(id, seller);
        if (updatedSeller != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller patched successfully", updatedSeller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller not found", null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete seller by ID",
            description = "Removes a seller from the system using their unique ID."
    )
    public ResponseEntity<ApiResponse<Void>> deleteSeller(@PathVariable Long id) {
        boolean isDeleted = sellerService.deleteSeller(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Seller deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Seller ID not found", null));
        }
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get seller by email",
            description = "Fetches a seller's details using their email address."
    )
    public ResponseEntity<ApiResponse<Seller>> getSellerByEmail(@PathVariable String email){
        Seller seller = sellerService.getSellerByEmail(email);
        if(seller!=null){
            ApiResponse<Seller> response = new ApiResponse<>(200,"Seller found by email "+email,seller);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponse<Seller> response = new ApiResponse<>(404,"Seller not found by email "+email,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/phone/{phone}")
    @Operation(
            summary = "Get seller by phone",
            description = "Fetches a seller's details using their phone number."
    )
    public ResponseEntity<ApiResponse<Seller>> getSellerByPhone(@PathVariable String phone){
        Seller seller = sellerService.getSellerByPhone(phone);
        if(seller!=null){
            ApiResponse<Seller> response = new ApiResponse<>(200,"Seller found by phone "+phone,seller);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponse<Seller> response = new ApiResponse<>(404,"Seller not found by phone "+phone,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
