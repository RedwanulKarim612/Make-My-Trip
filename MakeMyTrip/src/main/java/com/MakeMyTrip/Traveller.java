package com.MakeMyTrip;

public class Traveller {
    String travellerId;
    String Name;
    String identificationType;
    String identificationNo;

    public Traveller() {
    }

    public Traveller(String travellerId, String name, String identificationType, String identificationNo) {
        this.travellerId = travellerId;
        Name = name;
        this.identificationType = identificationType;
        this.identificationNo = identificationNo;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }
}


