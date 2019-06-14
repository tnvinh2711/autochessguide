package com.zinzin.tierbuilder.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceList {
    private int imgRace;
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

    public int getImgRace() {
        return imgRace;
    }

    public void setImgRace(int imgRace) {
        this.imgRace = imgRace;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
