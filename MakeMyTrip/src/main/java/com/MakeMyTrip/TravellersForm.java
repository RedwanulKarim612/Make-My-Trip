package com.MakeMyTrip;

import java.util.ArrayList;
import java.util.List;

public class TravellersForm {
    private List<Traveller> travellers;
    private List<String> tripIds;
    private String type;

    public TravellersForm() {
        travellers = new ArrayList<>();
        tripIds = new ArrayList<>();
    }

    public TravellersForm(List<Traveller> travellers, List<String> tripIds) {
        this.travellers = travellers;
        this.tripIds = tripIds;
    }

    public TravellersForm(int requestedTickets) {
        travellers = new ArrayList<>();
        for(int i=0;i<requestedTickets;i++){
            travellers.add(new Traveller());
        }
        tripIds = new ArrayList<>();
    }

    public TravellersForm(int requestedTickets, int numberOfTrips) {
        travellers = new ArrayList<>();
        for(int i=0;i<requestedTickets;i++){
            travellers.add(new Traveller());
        }
        tripIds = new ArrayList<>();
        for(int i=0;i<numberOfTrips;i++){
            tripIds.add("");
        }
    }

    public List<Traveller> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<Traveller> travellers) {
        this.travellers = travellers;
    }

    public List<String> getTripIds() {
        return tripIds;
    }

    public void setTripIds(List<String> tripIds) {
        this.tripIds = tripIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
