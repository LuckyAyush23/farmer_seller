package com.xpressbees.farmersellerapp.Farmer_Seller.Repository;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller , Long> {

    Seller findByEmail(String email);
    Seller findByPhone(String phone);
}
