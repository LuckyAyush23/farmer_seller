package com.xpressbees.farmersellerapp.Farmer_Seller.service;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    List<Inventory> getAllInventories();
    Inventory getInventoryById(Long id);
    Inventory updateInventory(Long id, Inventory inventory);
    boolean deleteInventory(Long id);
    boolean deleteInventoryByVegetableName(String vegetableName);
    Inventory findInventoryByVegetableName(String vegetableName);
    Inventory patchInventory(Long id, Inventory inventory);
}
