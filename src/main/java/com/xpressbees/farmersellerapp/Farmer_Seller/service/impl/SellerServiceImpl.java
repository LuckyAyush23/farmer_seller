package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.SellerRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {

        Optional<Seller> existingSellerOptional = sellerRepository.findById(id);
        if(existingSellerOptional.isPresent()){
        Seller existingSeller = existingSellerOptional.get();
        existingSeller.setName(seller.getName());
        existingSeller.setEmail(seller.getEmail());
        existingSeller.setPhone(seller.getPhone());
        existingSeller.setSellerAddress(seller.getSellerAddress());

        return sellerRepository.save(existingSeller);

        }
        return null;
    }

    @Override
    public Seller patchSeller(Long id , Map<String , Object> updates) {
        Optional<Seller> existingSellerOptional = sellerRepository.findById(id);

        if(existingSellerOptional.isPresent()){
            Seller existingSeller = existingSellerOptional.get();

            if(updates.containsKey("name") && updates.get("name")!=null) {
                existingSeller.setName((String) updates.get("name"));
            }
            if(updates.containsKey("email") && updates.get("email")!=null) {
                existingSeller.setName((String) updates.get("email"));
            }
            if(updates.containsKey("phone") && updates.get("phone")!=null) {
                existingSeller.setName((String) updates.get("phone"));
            }
            if(updates.containsKey("billingAddress") && updates.get("billingAddress")!=null) {
                existingSeller.setName((String) updates.get("billingAddress"));
            }

            return sellerRepository.save(existingSeller);
        }
            return null;
    }

    @Override
    public boolean deleteSeller(Long id) {
          Optional<Seller> seller = sellerRepository.findById(id);
          if(seller.isPresent()){
              sellerRepository.deleteById(id);
              return true;
          }
          else{
              return false;
          }
    }


}
