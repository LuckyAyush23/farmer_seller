package com.xpressbees.farmersellerapp.Farmer_Seller.exception;

import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponses<String>> handleException(Exception ex) {
        ApiResponses<String> response = new ApiResponses<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiResponse<String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        String errorMessage = "Duplicate entry: A seller with the same email or phone number already exists.";
//
//        ApiResponse<String> response = new ApiResponse<>(500, errorMessage, null);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ApiResponses<String>> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        ApiResponses<String> response = new ApiResponses<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
