package com.example.pospointofsale.objects;

import java.io.Serializable;

public class sold_product implements Serializable {

    String name;
    int quantity;
    int total_price;
    String barcode;
    public sold_product(){

    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public sold_product(String name, int quantity , int total_price , String barcode) {
        this.name = name;
        this.quantity = quantity;
        this.total_price = total_price;
        this.barcode = barcode;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
