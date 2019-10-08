package com.zinzin.tierbuilder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int imgItem;
    private String urlItem;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bonus")
    @Expose
    private List<String> bonus = new ArrayList<>();
    @SerializedName("combine")
    @Expose
    private List<String> combine = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getImgItem() {
        return imgItem;
    }

    public void setImgItem(int imgItem) {
        this.imgItem = imgItem;
    }

    public String getUrlItem() {
        return urlItem;
    }

    public void setUrlItem(String urlItem) {
        this.urlItem = urlItem;
    }
}