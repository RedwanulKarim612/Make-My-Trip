package com.MakeMyTrip;

public class Ticket {
    private String ticketId;
    private double price;
    private String type;
    private String seatNo;
    private String travellerId;
    private String tripId;
    private String boughtBy;

    public Ticket(String ticketId, double price, String type, String seatNo, String travellerId, String tripId, String boughtBy) {
        this.ticketId = ticketId;
        this.price = price;
        this.type = type;
        this.seatNo = seatNo;
        this.travellerId = travellerId;
        this.tripId = tripId;
        this.boughtBy = boughtBy;
    }

    public Ticket() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(String boughtBy) {
        this.boughtBy = boughtBy;
    }
}
