package com.xpressbees.farmersellerapp.Farmer_Seller.Repository;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findInventoryByVegetableName(String vegetableName);
    boolean deleteInventoryByVegetableName(String vegetableName);

}
