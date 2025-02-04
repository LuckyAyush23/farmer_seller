package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setName("Rahul");
        seller.setEmail("rahul@example.com");
        seller.setPhone("1234567890");
        seller.setSellerAddress("123 Farmer Lane");
    }

    @Test
    void testCreateSeller_Success() {
        when(sellerRepository.save(seller)).thenReturn(seller);
        Seller savedSeller = sellerService.createSeller(seller);

        assertNotNull(savedSeller);
        assertEquals("Rahul", savedSeller.getName());
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testCreateSeller_DuplicateEmail_ThrowsException() {
        when(sellerRepository.save(seller)).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sellerService.createSeller(seller);
        });

        assertEquals("Duplicate phone or email is not possible , it should be unique", exception.getMessage());
    }

    @Test
    void testGetSellerById_Success() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        Seller foundSeller = sellerService.getSellerById(1L);

        assertNotNull(foundSeller);
        assertEquals("Rahul", foundSeller.getName());
        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSellerById_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        Seller foundSeller = sellerService.getSellerById(1L);

        assertNull(foundSeller);
        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateSeller_Success() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller updatedSeller = new Seller();
        updatedSeller.setName("Jane Doe");

        Seller result = sellerService.updateSeller(1L, updatedSeller);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testUpdateSeller_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        Seller updatedSeller = new Seller();
        updatedSeller.setName("Jane Doe");

        Seller result = sellerService.updateSeller(1L, updatedSeller);

        assertNull(result);
        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void testPatchSeller_Success() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller patchSeller = new Seller();
        patchSeller.setEmail("newemail@example.com");

        Seller result = sellerService.patchSeller(1L, patchSeller);

        assertNotNull(result);
        assertEquals("newemail@example.com", result.getEmail());
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testPatchSeller_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        Seller patchSeller = new Seller();
        patchSeller.setEmail("newemail@example.com");

        Seller result = sellerService.patchSeller(1L, patchSeller);

        assertNull(result);
        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteSeller_Success() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        boolean result = sellerService.deleteSeller(1L);

        assertTrue(result);
        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSeller_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = sellerService.deleteSeller(1L);

        assertFalse(result);
        verify(sellerRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetSellerByEmail_Success() {
        when(sellerRepository.findByEmail("rahul@example.com")).thenReturn(seller);

        Seller foundSeller = sellerService.getSellerByEmail("rahul@example.com");

        assertNotNull(foundSeller);
        assertEquals("Rahul", foundSeller.getName());
        verify(sellerRepository, times(1)).findByEmail("rahul@example.com");
    }

    @Test
    void testGetSellerByPhone_Success() {
        when(sellerRepository.findByPhone("1234567890")).thenReturn(seller);

        Seller foundSeller = sellerService.getSellerByPhone("1234567890");

        assertNotNull(foundSeller);
        assertEquals("Rahul", foundSeller.getName());
        verify(sellerRepository, times(1)).findByPhone("1234567890");
    }
}
