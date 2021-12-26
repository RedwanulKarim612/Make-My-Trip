package com.MakeMyTrip;

public class Model {
    private String modelId;
    private String type;
    private int businessSeats;
    private int economicSeats;

    public Model(String modelId, String type, int businessSeats, int economicSeats) {
        this.modelId = modelId;
        this.type = type;
        this.businessSeats = businessSeats;
        this.economicSeats = economicSeats;
    }

    public Model() {

    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }

    public void setBusinessSeats(int businessSeats) {
        this.businessSeats = businessSeats;
    }

    public int getEconomicSeats() {
        return economicSeats;
    }

    public void setEconomicSeats(int economicSeats) {
        this.economicSeats = economicSeats;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId='" + modelId + '\'' +
                ", type='" + type + '\'' +
                ", businessSeats=" + businessSeats +
                ", economicSeats=" + economicSeats +
                '}';
    }
}
