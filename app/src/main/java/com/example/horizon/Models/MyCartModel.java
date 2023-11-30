package com.example.horizon.Models;

public class MyCartModel {
    String productName;
    String currentDate;
    String currentTime;


    public MyCartModel() {
    }

    public MyCartModel(String productName, String currentDate, String currentTime) {
        this.productName = productName;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
