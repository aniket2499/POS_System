package com.example.pospointofsale.objects;

import java.io.Serializable;

public class Item_details implements Serializable {
    private String mitemname;
    private String mitemimage;
    private String mitemprice;
    private String mitemquantity;
    private String mitembarcode;
    private String mitemunit;

    public Item_details(){
    }

    public Item_details(String mitemname, String mitemimage, String mitemprice, String mitemquantity, String mitembarcode, String mitemunit) {
        this.mitemname = mitemname;
        this.mitemimage = mitemimage;
        this.mitemprice = mitemprice;
        this.mitemquantity = mitemquantity;
        this.mitembarcode = mitembarcode;
        this.mitemunit = mitemunit;
    }

    public String getMitemname() {
        return mitemname;
    }

    public void setMitemname(String mitemname) {
        this.mitemname = mitemname;
    }

    public String getMitemimage() {
        return mitemimage;
    }

    public void setMitemimage(String mitemimage) {
        this.mitemimage = mitemimage;
    }

    public String getMitemprice() {
        return mitemprice;
    }

    public void setMitemprice(String mitemprice) {
        this.mitemprice = mitemprice;
    }

    public String getMitemquantity() {
        return mitemquantity;
    }

    public void setMitemquantity(String mitemquantity) {
        this.mitemquantity = mitemquantity;
    }

    public String getMitembarcode() {
        return mitembarcode;
    }

    public void setMitembarcode(String mitembarcode) {
        this.mitembarcode = mitembarcode;
    }

    public String getMitemunit() {
        return mitemunit;
    }

    public void setMitemunit(String mitemunit) {
        this.mitemunit = mitemunit;
    }
}
