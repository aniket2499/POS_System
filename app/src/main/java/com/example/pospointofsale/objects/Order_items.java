package com.example.pospointofsale.objects;

import java.io.Serializable;

public class Order_items implements Serializable{
        String mitemnewquantity;
        String mitemquantity;
        String mitemprice;
        String path;
        String name;
        String firebase_barcode;
        String firebase_user;
        String imageURL;


    public String getFirebase_barcode() {
        return firebase_barcode;
    }

    public void setFirebase_barcode(String firebase_barcode) {
        this.firebase_barcode = firebase_barcode;
    }

    public String getFirebase_user() {
        return firebase_user;
    }

    public void setFirebase_user(String firebase_user) {
        this.firebase_user = firebase_user;
    }

    public Order_items(){
    }

    public Order_items(String imageURL,String mitemnewquantity, String mitemquantity, String mitemprice, String path, String name, String firebase_barcode, String firebase_user) {
        this.mitemnewquantity = mitemnewquantity;
        this.mitemquantity = mitemquantity;
        this.mitemprice = mitemprice;
        this.path = path;
        this.name = name;
        this.firebase_barcode = firebase_barcode;
        this.firebase_user = firebase_user;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMitemnewquantity() {
        return mitemnewquantity;
    }

    public void setMitemnewquantity(String mitemnewquantity) {
        this.mitemnewquantity = mitemnewquantity;
    }

    public String getMitemquantity() {
        return mitemquantity;
    }

    public void setMitemquantity(String mitemquantity) {
        this.mitemquantity = mitemquantity;
    }

    public String getMitemprice() {
        return mitemprice;
    }

    public void setMitemprice(String mitemprice) {
        this.mitemprice = mitemprice;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
