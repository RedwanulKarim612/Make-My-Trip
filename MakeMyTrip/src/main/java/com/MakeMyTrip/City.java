package com.MakeMyTrip;

public class City {
    private String cityId;
    private String cityName;
    private double timeZone;
    private String countryId;

    public City(String cityId, String cityName, double timeZone, String countryId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.timeZone = timeZone;
        this.countryId = countryId;
    }

    public City() {

    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", timeZone=" + timeZone +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}
