package com.xpressbees.farmersellerapp.Farmer_Seller.service.impl;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Inventory;
import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;
import com.xpressbees.farmersellerapp.Farmer_Seller.Model.Seller;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.InventoryRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.OrderRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.Repository.SellerRepository;
import com.xpressbees.farmersellerapp.Farmer_Seller.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private SellerRepository sellerRepository;
    private InventoryRepository inventoryRepository;


    public OrderServiceImpl(OrderRepository orderRepository, SellerRepository sellerRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.sellerRepository = sellerRepository;
    }


    public PurchaseOrder createOrder(PurchaseOrder purchaseOrder) {
        // Step 1: Validate and fetch Seller
        Seller seller = sellerRepository.findById(purchaseOrder.getSeller().getId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Step 2: Validate and fetch Inventory
        Inventory inventory = inventoryRepository.findById(purchaseOrder.getPurchasedInventory().getId())
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        // Step 3: Calculate total price (quantityPurchased * pricePerUnit of the inventory)
        Double totalPrice = purchaseOrder.getQuantityPurchased() * inventory.getPricePerKg();
        purchaseOrder.setTotalPrice(totalPrice);

        // Step 4: Update Inventory Quantity
        int remainingQuantity = inventory.getVegetableStockLeft() - purchaseOrder.getQuantityPurchased();
        if (remainingQuantity < 0) {
            throw new RuntimeException("Insufficient inventory quantity");
        }
        inventory.setVegetableStockLeft(remainingQuantity);
        inventoryRepository.save(inventory);

        // Step 5: Save PurchaseOrder
        purchaseOrder.setSeller(seller);
        purchaseOrder.setPurchasedInventory(inventory);
        return orderRepository.save(purchaseOrder);
    }

    public PurchaseOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public PurchaseOrder updateOrder(Long id, PurchaseOrder updatedOrder) {
        PurchaseOrder existingOrder = getOrderById(id);
        if (existingOrder != null) {
            Seller seller = sellerRepository.findById(updatedOrder.getSeller().getId())
                    .orElseThrow(() -> new RuntimeException("Seller not found"));

            // Step 2: Validate and fetch Inventory
            Inventory inventory = inventoryRepository.findById(updatedOrder.getPurchasedInventory().getId())
                    .orElseThrow(() -> new RuntimeException("Inventory item not found"));

            // Step 3: Calculate total price (quantityPurchased * pricePerUnit of the inventory)
            Double totalPrice = updatedOrder.getQuantityPurchased() * inventory.getPricePerKg();
            updatedOrder.setTotalPrice(totalPrice);

            if(updatedOrder.getQuantityPurchased()>existingOrder.getQuantityPurchased()){
                int remainingQuantity = updatedOrder.getQuantityPurchased() - existingOrder.getQuantityPurchased();
                if (remainingQuantity > inventory.getVegetableStockLeft()) {
                    throw new RuntimeException("Insufficient inventory quantity");
                }
                inventory.setVegetableStockLeft(inventory.getVegetableStockLeft()-remainingQuantity);
            }
            else{
                int remainingQuantity = existingOrder.getQuantityPurchased() - updatedOrder.getQuantityPurchased() ;
                inventory.setVegetableStockLeft(inventory.getVegetableStockLeft()+remainingQuantity);
            }


            existingOrder.setSeller(seller);
            existingOrder.setPurchasedInventory(inventory);
            existingOrder.setQuantityPurchased(updatedOrder.getQuantityPurchased());
            existingOrder.setBuyerName(updatedOrder.getBuyerName());
            existingOrder.setBuyerPhone(updatedOrder.getBuyerPhone());
            existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
            existingOrder.setTotalPrice(totalPrice);
            existingOrder.setInvoiceNumber(updatedOrder.getInvoiceNumber());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());

            inventoryRepository.save(inventory);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public PurchaseOrder patchOrder(Long id, PurchaseOrder updatedOrder) {
        PurchaseOrder existingOrder = getOrderById(id);
        if(existingOrder!=null) {
            Double totalPrice = updatedOrder.getQuantityPurchased() * existingOrder.getPurchasedInventory().getPricePerKg();
            existingOrder.setTotalPrice(totalPrice);

            if(updatedOrder.getQuantityPurchased()>existingOrder.getQuantityPurchased()){
                int remainingQuantity = updatedOrder.getQuantityPurchased() - existingOrder.getQuantityPurchased();
                if (remainingQuantity > existingOrder.getPurchasedInventory().getVegetableStockLeft()) {
                    throw new RuntimeException("Insufficient inventory quantity");
                }
                existingOrder.getPurchasedInventory().setVegetableStockLeft(existingOrder.getPurchasedInventory().getVegetableStockLeft()-remainingQuantity);
            }
            else{
                int remainingQuantity = existingOrder.getQuantityPurchased() - updatedOrder.getQuantityPurchased() ;
                existingOrder.getPurchasedInventory().setVegetableStockLeft(existingOrder.getPurchasedInventory().getVegetableStockLeft()+remainingQuantity);
            }
            if (updatedOrder.getBuyerName() != null) {
                existingOrder.setBuyerName(updatedOrder.getBuyerName());
            }
            if (updatedOrder.getBuyerPhone() != null) {
                existingOrder.setBuyerPhone(updatedOrder.getBuyerPhone());
            }
            if (updatedOrder.getShippingAddress() != null) {
                existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
            }
            if (updatedOrder.getInvoiceNumber() != null) {
                existingOrder.setInvoiceNumber(updatedOrder.getInvoiceNumber());
            }
            if (updatedOrder.getQuantityPurchased() != null) {
                existingOrder.setQuantityPurchased(updatedOrder.getQuantityPurchased());
            }
            inventoryRepository.save(existingOrder.getPurchasedInventory());
            return orderRepository.save(existingOrder);
        }
        else{
            throw new RuntimeException("order not found");
        }
    }

    public boolean deleteOrderById(Long id) {
        PurchaseOrder existingOrder = getOrderById(id);
        if(existingOrder!=null) {
            orderRepository.delete(existingOrder);
            return true;
        }
        return false;
    }
}


