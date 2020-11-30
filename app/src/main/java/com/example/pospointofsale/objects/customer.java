package com.example.pospointofsale.objects;

import java.io.Serializable;

public class customer implements Serializable {
    String name;
    String number;
    int purchase;

    public customer(){

    }

    public customer(String name, String number, int purchase) {
        this.name = name;
        this.number = number;
        this.purchase = purchase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }
}
