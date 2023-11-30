package com.example.horizon.Models;

import java.io.Serializable;

public class PopularModel implements Serializable {
    String name;
    String img_url;
    String description;

    int price;

    public PopularModel() {
    }

    public PopularModel(String name, String img_url, String description, int price) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}




