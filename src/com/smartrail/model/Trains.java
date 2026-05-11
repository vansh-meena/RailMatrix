package com.smartrail.model;

public class Trains {
    private String trainName;
    private String trainType;
    private String departure;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private double baseFare;
    private double farePerKm;

    public Trains(String trainName, String trainType, String departure, String destination, int totalSeats, double baseFare, double farePerKm) {
        this.trainName = trainName;
        this.trainType = trainType;
        this.departure = departure;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.baseFare = baseFare;
        this.farePerKm = farePerKm;
    }

    public String getTrainName() { return trainName; }
    public String getTrainType() { return trainType; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getBaseFare() { return baseFare; }
    public double getFarePerKm() { return farePerKm; }
}
