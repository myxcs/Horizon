package com.example.horizon.Models;

import java.io.Serializable;

public class NewGamesModel implements Serializable {

    // i dont have data form admin app to make it more, their data make me feel bad
    String name;
    String img_url;
    String description;
    String dungluong;

    int gia;
     int maloai;
     int soluongtai;

    public NewGamesModel() {
    }

    public NewGamesModel(String name, String img_url, String description, String dungluong, int gia, int maloai, int soluongtai) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.dungluong = dungluong;
        this.gia= gia;
        this.maloai = maloai;
        this.soluongtai = soluongtai;
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

    public String getDungluong() {
        return dungluong;
    }

    public void setDungluong(String dungluong) {
        this.dungluong = dungluong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public int getSoluongtai() {
        return soluongtai;
    }

    public void setSoluongtai(int soluongtai) {
        this.soluongtai = soluongtai;
    }
}
