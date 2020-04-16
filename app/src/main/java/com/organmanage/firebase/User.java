package com.organmanage.firebase;

public class User {


  public   String email;

    public User(String email, String name, String city, String country, String phone, String userType) {
        this.email = email;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.userType = userType;
        this.country=country;
    }

    public  User(){}



    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String name;
    public String city;
    public String phone;

    public  String  country;
    public String userType;



}
