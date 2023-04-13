package com.example.logreg;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.time.LocalTime;

public class Trip {
    private String id_driver;
    private int passengers;
    private String firstPass_id;
    private String secondPass_id;
    private String thirdPass_id;
    private String startCity;
    private String endCity;
    private String date;
    private String starttime;
    private String endtime;
    private int duration;
    private int price;
    private String driverName;
    private String type;

    public Trip(String id_driver, int passengers, String firstPass_id, String secondPass_id, String thirdPass_id, String startCity, String endCity, String date, String starttime, String endtime, int duration, int price, String driverName, String type) {
        this.id_driver = id_driver;
        this.passengers = passengers;
        this.firstPass_id = firstPass_id;
        this.secondPass_id = secondPass_id;
        this.thirdPass_id = thirdPass_id;
        this.startCity = startCity;
        this.endCity = endCity;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.duration = duration;
        this.price = price;
        this.driverName = driverName;
        this.type = type;
    }
    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getFirstPass_id() {
        return firstPass_id;
    }

    public void setFirstPass_id(String firstPass_id) {
        this.firstPass_id = firstPass_id;
    }

    public String getSecondPass_id() {
        return secondPass_id;
    }

    public void setSecondPass_id(String secondPass_id) {
        this.secondPass_id = secondPass_id;
    }

    public String getThirdPass_id() {
        return thirdPass_id;
    }

    public void setThirdPass_id(String thirdPass_id) {
        this.thirdPass_id = thirdPass_id;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Trip() {
    }

    public void saveToFirebase()
    {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tripsRef = database.child("Trips");
        String key = tripsRef.push().getKey();
        tripsRef.child(key).setValue(this);
    }
}