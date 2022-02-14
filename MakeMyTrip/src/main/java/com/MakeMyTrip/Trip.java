package com.MakeMyTrip;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {
    private String tripId;
    private double basePrice;
    private double upgradePct;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private double duration;
    private String vehicleId;
    private String startFrom;
    private String destination;

    public Trip() {
    }

    public Trip(String tripId, double basePrice, double upgradePct, Date date, double duration, String vehicleId, String startFrom, String destination) {
        this.tripId = tripId;
        this.basePrice = basePrice;
        this.upgradePct = upgradePct;
        this.date = date;
        this.duration = duration;
        this.vehicleId = vehicleId;
        this.startFrom = startFrom;
        this.destination = destination;
        System.out.println(date);
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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
        System.out.println(date);
        this.date = date;
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

    @Override
    public String toString() {
        return "Trip{" +
                "tripId='" + tripId + '\'' +
                ", basePrice=" + basePrice +
                ", upgradePct=" + upgradePct +
                ", date=" + date +
                ", duration=" + duration +
                ", vehicleId='" + vehicleId + '\'' +
                ", startFrom='" + startFrom + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public void setDate(String start_time)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            this.date = dateFormat.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
