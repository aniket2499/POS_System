package com.example.pospointofsale.objects;

import java.io.Serializable;
import java.util.List;

public class bill_display implements Serializable {
    String name;
    String mobile;
    String date;
    List<bill_items> li;

    public bill_display(){};

    public bill_display(String name, String mobile, String date, List<bill_items> li) {
        this.name = name;
        this.mobile = mobile;
        this.date = date;
        this.li = li;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<bill_items> getLi() {
        return li;
    }

    public void setLi(List<bill_items> li) {
        this.li = li;
    }
}
