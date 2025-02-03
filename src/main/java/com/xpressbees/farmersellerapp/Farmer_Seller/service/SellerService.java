package com.xpressbees.farmersellerapp.Farmer_Seller.service;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;

import java.util.Map;

public interface SellerService {
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id);
    Seller updateSeller(Long id,Seller seller);
    Seller patchSeller(Long id , Map<String , Object> updates);
    boolean deleteSeller(Long id);
}
