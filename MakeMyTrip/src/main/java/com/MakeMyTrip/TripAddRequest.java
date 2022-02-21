package com.MakeMyTrip;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TripAddRequest {
    private String tripId;
    private double basePrice;
    private double upgradePct;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;
    private double duration;
    private String vehicleId;
    private String startFrom;
    private String destination;
    private int numberOfTrips;

    public TripAddRequest() {

    }

    public TripAddRequest(String tripId, double basePrice, double upgradePct, Date date, double duration, String vehicleId, String startFrom, String destination, int numberOfTrips) {
        this.tripId = tripId;
        this.basePrice = basePrice;
        this.upgradePct = upgradePct;
        this.date = date;
        this.duration = duration;
        this.vehicleId = vehicleId;
        this.startFrom = startFrom;
        this.destination = destination;
        this.numberOfTrips = numberOfTrips;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getUpgradePct() {
        return upgradePct;
    }

    public void setUpgradePct(double upgradePct) {
        this.upgradePct = upgradePct;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    @Override
    public String toString() {
        return "TripAddRequest{" +
                "tripId='" + tripId + '\'' +
                ", basePrice=" + basePrice +
                ", upgradePct=" + upgradePct +
                ", date=" + date +
                ", duration=" + duration +
                ", vehicleId='" + vehicleId + '\'' +
                ", startFrom='" + startFrom + '\'' +
                ", destination='" + destination + '\'' +
                ", numberOfTrips=" + numberOfTrips +
                '}';
    }
}
