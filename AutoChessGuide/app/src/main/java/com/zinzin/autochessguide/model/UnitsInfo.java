package com.zinzin.autochessguide.model;

public class UnitsInfo {
    int imgInfo;
    String name;
    String type;
    String des;

    public UnitsInfo(int imgInfo, String name, String type, String des) {
        this.imgInfo = imgInfo;
        this.name = name;
        this.type = type;
        this.des = des;
    }

    public int getImgInfo() {
        return imgInfo;
    }

    public void setImgInfo(int imgInfo) {
        this.imgInfo = imgInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
