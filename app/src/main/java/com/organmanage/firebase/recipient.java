package com.organmanage.firebase;

public class recipient {

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public  String userGuid;
    public  String  id;
    public  String name;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public  String bloodGroup;
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganType(String organType) {
        this.organType = organType;
    }

    public void setRequirdDateTill(String requirdDateTill) {
        this.requirdDateTill = requirdDateTill;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public  String organType;
    public String requirdDateTill;

    public  boolean  isAssigned;

    public String getName() {
        return name;
    }

    public String getOrganType() {
        return organType;
    }

    public String getRequirdDateTill() {
        return requirdDateTill;
    }

    public  boolean getIsAssigned(){
        return  isAssigned;
    }

    public  String  getId (){
         return  id;
    }


    public recipient(String name, String organType, String requirdDateTill, boolean isAssigned,String bloodGroup) {
        this.name = name;
        this.organType = organType;
        this.requirdDateTill=requirdDateTill;
        this.isAssigned=isAssigned;
        this.bloodGroup=bloodGroup;
    }

    public recipient(){}



}
