package com.MakeMyTrip;

public class Location {
    private String locationId;
    private String address;
    private String cityId;

    public Location() {
    }

    public Location(String locationId, String address, String countryId) {
        this.locationId = locationId;
        this.address = address;
        this.cityId = countryId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
