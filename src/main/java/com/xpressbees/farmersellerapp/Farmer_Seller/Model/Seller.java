package com.xpressbees.farmersellerapp.Farmer_Seller.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sellers")
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    private String sellerAddress;

}

