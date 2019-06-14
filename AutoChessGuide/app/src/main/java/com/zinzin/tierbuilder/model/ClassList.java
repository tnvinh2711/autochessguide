package com.zinzin.tierbuilder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassList {
    private int count;
    private  int imgClass;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dota_convert")
    @Expose
    private String dotaConvert;
    @SerializedName("tier")
    @Expose
    private Integer tier;
    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("bonus")
    @Expose
    private List<String> bonus = null;

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

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<String> getBonus() {
        return bonus;
    }

    public void setBonus(List<String> bonus) {
        this.bonus = bonus;
    }

    public int getImgClass() {
        return imgClass;
    }

    public void setImgClass(int imgClass) {
        this.imgClass = imgClass;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
