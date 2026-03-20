package com.smartrail.model;

public class Trains {
    private String trainName;
    private String departure;
    private String destination;
    private int totalSeats;
    private int availableSeats;

    public Trains(String trainName, String source, String destination, int totalSeats) {
        this.trainName = trainName;
        this.departure = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // initially same
    }

    public String getTrainName() {
        return trainName;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
