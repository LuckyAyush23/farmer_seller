package com.xpressbees.farmersellerapp.Farmer_Seller.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchaseorder")
@Data
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory purchasedInventory;

    private Integer quantityPurchased;

    private String buyerName;

    @Column(length = 15)
    private String buyerPhone;

    private String shippingAddress;

    private Double totalPrice;

    private String invoiceNumber;

    private LocalDateTime orderDate = LocalDateTime.now();

}
