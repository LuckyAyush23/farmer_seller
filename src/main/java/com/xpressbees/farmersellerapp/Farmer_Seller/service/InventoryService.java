package com.xpressbees.farmersellerapp.Farmer_Seller.service;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;

import java.util.Map;
import java.util.Optional;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    Optional<Inventory> getInventoryById(Long id);
    Inventory updateInventory(Long id, Inventory inventory);
    boolean deleteInventory(Long id);
    boolean deleteInventoryByVegetableName(String vegetableName);
    Inventory findInventoryByVegetableName(String vegetableName);
    Inventory patchInventory(Long id, Map<String, Object> updates);
}
