package com.xpressbees.farmersellerapp.Farmer_Seller.service;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;

public interface SellerService {
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id);
    Seller updateSeller(Long id,Seller seller);
    Seller patchSeller(Long id , Seller seller);
    boolean deleteSeller(Long id);
    Seller getSellerByEmail(String email);
    Seller getSellerByPhone(String phone);
}
