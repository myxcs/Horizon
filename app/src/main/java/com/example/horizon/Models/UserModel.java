package com.example.horizon.Models;

import java.io.Serializable;

public class UserModel implements Serializable {
//    String uid;

    String name;
    String email;
    String password;

    String profileImg;
    int money;

    public UserModel() {

    }

    public UserModel(String name, String email, String password, String profileImg, int money) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.money = money;
    }
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }


    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    //    public int getMoney() {
//        return money;
//    }
//
//    public void setMoney(int money) {
//        this.money = money;
//    }

//    @Override
//    public String toString() {
//        return "UserModel{" +
//                "name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}

