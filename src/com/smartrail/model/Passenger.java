package com.smartrail.model;

public class Passenger {
    private String passenger_name;
    private int age;
    private String gender;

    public Passenger(String passenger_name, int age, String gender) {
        this.passenger_name = passenger_name;
        this.age = age;
        this.gender = gender;
    }

    public String getPassenger_name() { return passenger_name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
}
