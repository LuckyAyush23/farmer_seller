package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.SellerRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller createSeller(Seller seller) {
        try {
            return sellerRepository.save(seller);
        } catch (Exception ex) {
            throw new RuntimeException("Duplicate phone or email is not possible , it should be unique");
        }
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {

        Optional<Seller> existingSellerOptional = sellerRepository.findById(id);
        if (existingSellerOptional.isPresent()) {
            Seller existingSeller = existingSellerOptional.get();
            existingSeller.setName(seller.getName());
            existingSeller.setEmail(seller.getEmail());
            existingSeller.setPhone(seller.getPhone());

            existingSeller.setSellerAddress(seller.getSellerAddress());
            try {
                return sellerRepository.save(existingSeller);
            } catch (Exception e) {
                throw new RuntimeException("Duplicate phone or email is not possible , it should be unique");
            }
        }
        return null;
    }

    @Override
    public Seller patchSeller(Long id, @RequestBody Seller updatedSeller) {
        Optional<Seller> existingSellerOptional = sellerRepository.findById(id);

        if (existingSellerOptional.isPresent()) {
            Seller existingSeller = existingSellerOptional.get();

            // Update only non-null fields from request body
            if (updatedSeller.getName() != null) {
                existingSeller.setName(updatedSeller.getName());
            }
            if (updatedSeller.getEmail() != null) {
                existingSeller.setEmail(updatedSeller.getEmail());
            }
            if (updatedSeller.getPhone() != null) {
                existingSeller.setPhone(updatedSeller.getPhone());
            }
            if (updatedSeller.getSellerAddress() != null) {
                existingSeller.setSellerAddress(updatedSeller.getSellerAddress());
            }
            try {
                return sellerRepository.save(existingSeller);
            } catch (Exception e) {
                throw new RuntimeException("Duplicate phone or email is not possible , it should be unique");
            }
        }
        return null;
    }


    @Override
    public boolean deleteSeller(Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
            sellerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/email/{email}")
    public Seller getSellerByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public Seller getSellerByPhone(String phone) {
        return sellerRepository.findByPhone(phone);
    }

}
