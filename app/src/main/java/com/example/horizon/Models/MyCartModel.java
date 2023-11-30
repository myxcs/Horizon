package com.example.horizon.Models;

public class MyCartModel {
    String gameName;
    String currentDate;
    String currentTime;

    public MyCartModel() {
    }

    public MyCartModel(String gameName, String currentDate, String currentTime) {
        this.gameName = gameName;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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
