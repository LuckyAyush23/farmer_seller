package com.xpressbees.farmersellerapp.Farmer_Seller.service;

import com.xpressbees.farmersellerapp.Farmer_Seller.Model.PurchaseOrder;

public interface OrderService {

    PurchaseOrder createOrder(PurchaseOrder purchaseOrder);
    PurchaseOrder getOrderById(Long id);
    PurchaseOrder updateOrder(Long id,PurchaseOrder purchaseOrder);
    PurchaseOrder patchOrder(Long id,PurchaseOrder purchaseOrder);
    boolean deleteOrderById(Long id);
}
