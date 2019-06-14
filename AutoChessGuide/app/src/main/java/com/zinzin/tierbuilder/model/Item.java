package com.zinzin.tierbuilder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int imgItem;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dota_convert")
    @Expose
    private String dotaConvert;
    @SerializedName("bonus")
    @Expose
    private List<String> bonus = new ArrayList<>();
    @SerializedName("combine")
    @Expose
    private List<String> combine = new ArrayList<>();
    @SerializedName("dota_convert_cb")
    @Expose
    private List<String> dotaConvertCb = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDotaConvert() {
        return dotaConvert;
    }

    public void setDotaConvert(String dotaConvert) {
        this.dotaConvert = dotaConvert;
    }

    public List<String> getBonus() {
        return bonus;
    }

    public void setBonus(List<String> bonus) {
        this.bonus = bonus;
    }

    public List<String> getCombine() {
        return combine;
    }

    public void setCombine(List<String> combine) {
        this.combine = combine;
    }

    public List<String> getDotaConvertCb() {
        return dotaConvertCb;
    }

    public void setDotaConvertCb(List<String> dotaConvertCb) {
        this.dotaConvertCb = dotaConvertCb;
    }

    public int getImgItem() {
        return imgItem;
    }

    public void setImgItem(int imgItem) {
        this.imgItem = imgItem;
    }
}