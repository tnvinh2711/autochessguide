package com.zinzin.tierbuilder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaceList {
    private String imgRace;
    private int count;
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
    private List<Bonus> bonus;

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

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }

    public String getImgRace() {
        return imgRace;
    }

    public void setImgRace(String imgRace) {
        this.imgRace = imgRace;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
