package com.example.pospointofsale.objects;

public class company {
    public String company_name;
    public user user1;

    public company(){

    }
    public company(String company_name , user user1) {
        this.company_name = company_name;
        this.user1 = user1;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public user getUser1() {
        return user1;
    }

    public void setUser1() {
        setUser1();
    }

    public void setUser1(user user1) {
        this.user1 = user1;
    }
}
