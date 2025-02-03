package com.xpressbees.farmersellerapp.Farmer_Seller.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String vegetableName;
    @Column(nullable = false)
    private int vegetableStockLeft;
    @Column(nullable = false)
    private Double pricePerKg;
}
