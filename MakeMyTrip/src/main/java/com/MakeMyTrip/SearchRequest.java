package com.MakeMyTrip;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchRequest {
    private String startingCity;
    private String destinationCity;
    private String type;
    private int numberOfTravellers;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date travellingDate;
    private String order;
    public SearchRequest() {
        order = "PRICE";
    }

    public SearchRequest(String startingCity, String destinationCity, String type, int numberOfTravellers, Date travellingDate) {
        this.startingCity = startingCity;
        this.destinationCity = destinationCity;
        this.type = type;
        this.numberOfTravellers = numberOfTravellers;
        this.travellingDate = travellingDate;
        this.order = "PRICE";
    }
    public SearchRequest(String startingCity, String destinationCity, String type, int numberOfTravellers, Date travellingDate, String order) {
        this.startingCity = startingCity;
        this.destinationCity = destinationCity;
        this.type = type;
        this.numberOfTravellers = numberOfTravellers;
        this.travellingDate = travellingDate;
        this.order = order;
    }

    public SearchRequest(SearchRequest sr) {
        this.startingCity = sr.startingCity;
        this.destinationCity = sr.destinationCity;
        this.type = sr.type;
        this.numberOfTravellers = sr.numberOfTravellers;
        this.travellingDate = sr.travellingDate;
        this.order = sr.order;
    }

    public String getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(String startingCity) {
        this.startingCity = startingCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    public Date getTravellingDate() {
        return travellingDate;
    }

    public void setTravellingDate(Date travellingDate) {
        this.travellingDate = travellingDate;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "startingCity='" + startingCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", type='" + type + '\'' +
                ", numberOfTravellers=" + numberOfTravellers +
                ", travellingDate=" + travellingDate +
                ", order='" + order + '\'' +
                '}';
    }
}
