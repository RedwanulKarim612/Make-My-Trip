package com.MakeMyTrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Plan {
    private List<Trip> trips;
    private double price;
    private int requestedTickets;
    private int numberOfTrips;
    private String type;
    public double totalDuration;
    public Plan(List<Trip> trips) {
        this.trips = trips;
    }

    public Plan() {
        trips = new ArrayList<>();
    }

    public Plan(List<Trip> trips, double price, int requestedTickets, int numberOfTrips) {
        this.trips = trips;
        this.price = price;
        this.requestedTickets = requestedTickets;
        this.numberOfTrips = numberOfTrips;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRequestedTickets() {
        return requestedTickets;
    }

    public void setRequestedTickets(int requestedTickets) {
        this.requestedTickets = requestedTickets;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void addTrip(Trip t){
        trips.add(t);
    }


    public void organize(SearchRequest req) {
        Collections.reverse(trips);
        numberOfTrips = trips.size();
        this.requestedTickets = req.getNumberOfTravellers();
        price = 0;
        this.type = req.getType();
        boolean extra = (req.getType().equals("BUSINESS"));
        for (Trip t : trips){
            price += t.getBasePrice();
            if(extra)price += t.getBasePrice() * t.getUpgradePct() / 100.0;
        }
    }

    @Override
    public String toString() {
        return "Plan{" +
                "trips=" + trips +
                ", price=" + price +
                ", requestedTickets=" + requestedTickets +
                ", numberOfTrips=" + numberOfTrips +
                ", type='" + type + '\'' +
                ", totalDuration=" + totalDuration +
                '}';
    }
}
