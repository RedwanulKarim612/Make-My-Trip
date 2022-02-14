package com.MakeMyTrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plan {
    private List<Trip> trips;
    private double price;
    private int requestedTickets;
    private int numberOfTrips;
    public Plan(List<Trip> trips) {
        this.trips = trips;
    }

    public Plan() {
        trips = new ArrayList<>();
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
    public void addTrip(Trip t){
        trips.add(t);
    }

    public void organize(SearchRequest req) {
        Collections.reverse(trips);
        numberOfTrips = trips.size();
        this.requestedTickets = req.getNumberOfTravellers();
        price = 0;
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
                '}';
    }
}
