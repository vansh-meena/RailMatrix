package com.smartrail.model;

public class Station {
    private int stationId;
    private String stationName;
    private String stationCity;

    public Station() {}

    public Station(int stationId, String stationName, String stationCity) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationCity = stationCity;
    }

    //SETTER FUNCTIONS
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public void setStationCity(String stationCity) {
        this.stationCity = stationCity;
    }

    //GETTER FUNCTIONS
    public int getStationId() {
        return stationId;
    }
    public String getStationName() {
        return stationName;
    }
    public String getStationCity() {
        return stationCity;
    }
}
