package com.xpressbees.farmersellerapp.Farmer_Seller.controller;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Util.ApiResponse;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerControllerTest {

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setId(1L);
        seller.setName("Rahul");
        seller.setEmail("rahul@example.com");
        seller.setPhone("1234567890");
        seller.setSellerAddress("123 Farmer Lane");
    }

    @Test
    void testCreateSeller_Success() {
        when(sellerService.createSeller(any(Seller.class))).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.createSeller(seller);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller created successfully", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testCreateSeller_DuplicateEmailOrPhone() {
        when(sellerService.createSeller(any(Seller.class))).thenThrow(new RuntimeException("Seller email or phone cannot be duplicate"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sellerController.createSeller(seller);
        });

        assertEquals("Seller email or phone cannot be duplicate", exception.getMessage());
    }

    @Test
    void testGetSellerById_Success() {
        when(sellerService.getSellerById(1L)).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller fetched successfully", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testGetSellerById_NotFound() {
        when(sellerService.getSellerById(1L)).thenReturn(null);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testUpdateSeller_Success() {
        when(sellerService.updateSeller(1L, seller)).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.updateSeller(1L, seller);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller updated successfully", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testUpdateSeller_NotFound() {
        when(sellerService.updateSeller(1L, seller)).thenReturn(null);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.updateSeller(1L, seller);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testPatchSeller_Success() {
        when(sellerService.patchSeller(1L, seller)).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.patchSeller(1L, seller);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller patched successfully", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testPatchSeller_NotFound() {
        when(sellerService.patchSeller(1L, seller)).thenReturn(null);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.patchSeller(1L, seller);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteSeller_Success() {
        when(sellerService.deleteSeller(1L)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = sellerController.deleteSeller(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteSeller_NotFound() {
        when(sellerService.deleteSeller(1L)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = sellerController.deleteSeller(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller ID not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testGetSellerByEmail_Success() {
        when(sellerService.getSellerByEmail("rahul@example.com")).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerByEmail("rahul@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller found by email rahul@example.com", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testGetSellerByEmail_NotFound() {
        when(sellerService.getSellerByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerByEmail("nonexistent@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller not found by email nonexistent@example.com", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testGetSellerByPhone_Success() {
        when(sellerService.getSellerByPhone("1234567890")).thenReturn(seller);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerByPhone("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller found by phone 1234567890", response.getBody().getMessage());
        assertEquals(seller, response.getBody().getData());
    }

    @Test
    void testGetSellerByPhone_NotFound() {
        when(sellerService.getSellerByPhone("0000000000")).thenReturn(null);

        ResponseEntity<ApiResponse<Seller>> response = sellerController.getSellerByPhone("0000000000");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller not found by phone 0000000000", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
