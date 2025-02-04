package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.InventoryRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.exception.InventoryNotFoundException;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {


    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        try {
            return inventoryRepository.save(inventory);
        }
        catch (Exception ex){
            throw new RuntimeException("Duplicate vegetable name , vegetable is already present");
        }
    }

    @Override
    public List<Inventory> getAllInventories() {

            return inventoryRepository.findAll();

    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for ID: " + id));
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
            try {
                return inventoryRepository.save(existingInventory);
            } catch (Exception e) {
                throw new RuntimeException("Duplicate vegetable name , vegetable is already present");
            }
        }
        return null;  // Return null if the item doesn't exist
    }

    @Override
    public Inventory patchInventory(Long id, Inventory updatedInventory) {
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);

        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();

            // Update only non-null fields from the request body
            if (updatedInventory.getVegetableName() != null) {
                existingInventory.setVegetableName(updatedInventory.getVegetableName());
            }
            if (updatedInventory.getVegetableStockLeft() != 0) {
                existingInventory.setVegetableStockLeft(updatedInventory.getVegetableStockLeft());
            }
            if (updatedInventory.getPricePerKg() != null) {
                existingInventory.setPricePerKg(updatedInventory.getPricePerKg());
            }

            try {
                return inventoryRepository.save(existingInventory);
            } catch (Exception e) {
                throw new RuntimeException("Duplicate vegetable name , vegetable is already present");
            }
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

