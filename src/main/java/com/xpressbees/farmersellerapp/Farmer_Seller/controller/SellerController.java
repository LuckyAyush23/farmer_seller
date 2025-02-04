package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponses;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/seller")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponses<Seller>> createSeller(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        if(createdSeller!=null){
            return ResponseEntity.ok(new ApiResponses<>(201, "Seller created successfully", seller));
        }
        else{
            throw new RuntimeException("Seller email or phone cannot be duplicate");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponses<Seller>> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        if (seller != null) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Seller fetched successfully", seller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponses<>(404, "Seller not found", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponses<Seller>> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.updateSeller(id, seller);
        if (updatedSeller != null) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Seller updated successfully", updatedSeller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponses<>(404, "Seller not found", null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponses<Seller>> patchSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.patchSeller(id, seller);
        if (updatedSeller != null) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Seller patched successfully", updatedSeller));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponses<>(404, "Seller not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponses<Void>> deleteSeller(@PathVariable Long id) {
        boolean isDeleted = sellerService.deleteSeller(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponses<>(200, "Seller deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponses<>(404, "Seller ID not found", null));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponses<Seller>> getSellerByEmail(@PathVariable String email){
        Seller seller = sellerService.getSellerByEmail(email);
        if(seller!=null){
            ApiResponses<Seller> response = new ApiResponses<>(200,"Seller found by email "+email,seller);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponses<Seller> response = new ApiResponses<>(404,"Seller not found by email "+email,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponses<Seller>> getSellerByPhone(@PathVariable String phone){
        Seller seller = sellerService.getSellerByPhone(phone);
        if(seller!=null){
            ApiResponses<Seller> response = new ApiResponses<>(200,"Seller found by phone "+phone,seller);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            ApiResponses<Seller> response = new ApiResponses<>(404,"Seller not found by phone "+phone,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}
