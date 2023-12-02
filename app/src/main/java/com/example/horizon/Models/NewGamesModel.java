package com.example.horizon.Models;

import java.io.Serializable;

public class NewGamesModel implements Serializable {

    // i dont have data form admin app to make it more, their data make me feel bad
    String name;
    String img_url;
    String description;
    String storage;
    int downloaded;
    int genre;
    int price;



    public NewGamesModel() {
    }

    public NewGamesModel(String name, String img_url, String description, String storage, int downloaded, int genre, int price) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.storage = storage;
        this.downloaded = downloaded;
        this.genre = genre;
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

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
