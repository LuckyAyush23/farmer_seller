package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.InventoryRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {


    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> getInventoryById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()){
            return inventory;
        }
        else{
            throw new InventoryNotFoundException("Inventory not found for ID: " + id);
        }
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);
        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();

            // Update fields without changing the ID
            existingInventory.setVegetableName(inventory.getVegetableName());
            existingInventory.setVegetableStockLeft(inventory.getVegetableStockLeft());
            existingInventory.setPricePerKg(inventory.getPricePerKg());

            return inventoryRepository.save(existingInventory);
        }
        return null;  // Return null if the item doesn't exist
    }

    @Override
    public Inventory patchInventory(Long id, Map<String, Object> updates) {
        // Find the existing inventory by ID
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);

        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();

            // Update only the fields provided in the 'updates' map
            if (updates.containsKey("vegetableName") && updates.get("vegetableName") != null) {
                existingInventory.setVegetableName((String) updates.get("vegetableName"));
            }
            if (updates.containsKey("vegetableQuantity") && updates.get("vegetableQuantity") != null) {
                existingInventory.setVegetableStockLeft((Integer) updates.get("vegetableQuantity"));
            }
            if (updates.containsKey("pricePerKg") && updates.get("pricePerKg") != null) {
                existingInventory.setPricePerKg((Double) updates.get("pricePerKg"));
            }

            // Save the updated inventory back to the repository
            return inventoryRepository.save(existingInventory);
        }

        return null; // Return null if inventory with the given ID doesn't exist
    }

    @Override
    public boolean deleteInventory(Long id) {
        // Check if the inventory with the given ID exists
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);

        if (existingInventoryOptional.isPresent()) {
            // If found, delete the inventory and return true
            inventoryRepository.deleteById(id);
            return true;
        }

        // Return false if the inventory with the given ID doesn't exist
        return false;
    }

    @Override
    public boolean deleteInventoryByVegetableName(String vegetableName) {
        Inventory inventory = inventoryRepository.findInventoryByVegetableName(vegetableName);
        if(inventory!=null){
            inventoryRepository.delete(inventory);
            return true;
        }
        return false;
    }

    @Override
    public Inventory findInventoryByVegetableName(String vegetableName) {
        Inventory inventory = inventoryRepository.findInventoryByVegetableName(vegetableName);
        if(inventory!=null){
            return inventory;
        }
        return null;
    }

//


}

