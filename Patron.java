package com.beginsecure;
/*
Nadim Ramrattan
CEN-3024C
6/18/2026
Library Management System
 */

// Class that represents a single library patron
public class Patron {
    private String id;
    private String name;
    private String address;
    private double fine;

    // constructor to make a new Patron object
    public Patron(String id, String name, String address, double fine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fine = fine;
    }

    // getters to grab info when needed
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    // setters in case something needs to be updated
    public double getFine() {
        return fine;
    }

    @Override
    // this is for printing the patron’s info
    public String toString() {
        return id + " | " + name + " | " + address + " | $" + fine;
    }
}
