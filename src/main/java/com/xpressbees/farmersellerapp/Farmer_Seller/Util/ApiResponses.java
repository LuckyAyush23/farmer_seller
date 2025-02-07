package com.xpressbees.farmersellerapp.Farmer_Seller.Util;

import lombok.Data;

@Data
public class ApiResponses<T> {

    private int status;
    private String message;
    private T data;

    public ApiResponses(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
