package com.organmanage.firebase;

import java.util.Date;

public class donar {

    public  String  id;
    public  String userGuid;
    public  String name;
    public  String organType;
    public  String availableDateOn;
    public  boolean isDonated;
    public  String city;
    public  String bloodGroup;

    public donar(String name, String organType,String availableDateOn,boolean isDonated,String city, String bloodGroup) {
        this.name = name;
        this.organType = organType;
        this.availableDateOn=availableDateOn;
        this.isDonated=isDonated;
        this.city=city;
        this.bloodGroup=bloodGroup;
    }

    public  String  getId (){
        return  id;
    }
    public  donar(){}

}
