package com.smartrail.model;

public class Route {

    private int routeId;
    private int departureStationId;
    private int destinationStationId;
    private int distanceKm;
    private int travelTimeMinutes;

    public Route(int routeId, int departureStationId, int destinationStationId,
                 int distanceKm, int travelTimeMinutes) {

        this.routeId = routeId;
        this.departureStationId = departureStationId;
        this.destinationStationId = destinationStationId;
        this.distanceKm = distanceKm;
        this.travelTimeMinutes = travelTimeMinutes;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getDepartureStationId() {
        return departureStationId;
    }

    public int getDestinationStationId() {
        return destinationStationId;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }
}
