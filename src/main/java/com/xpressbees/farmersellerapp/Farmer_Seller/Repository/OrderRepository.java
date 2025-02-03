package com.xpressbees.farmersellerapp.Farmer_Seller.Repository;


import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder,Long> {


}
