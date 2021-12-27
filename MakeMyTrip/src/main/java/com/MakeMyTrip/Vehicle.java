package com.MakeMyTrip;

public class Vehicle {
    private String vehicleId;
    private String registrationNo;
    private String ModelId;
    private String CompanyId;

    public Vehicle() {
    }

    public Vehicle(String vehicleId, String registrationNo, String modelId, String companyId) {
        this.vehicleId = vehicleId;
        this.registrationNo = registrationNo;
        ModelId = modelId;
        CompanyId = companyId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getModelId() {
        return ModelId;
    }

    public void setModelId(String modelId) {
        ModelId = modelId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", ModelId='" + ModelId + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                '}';
    }
}
