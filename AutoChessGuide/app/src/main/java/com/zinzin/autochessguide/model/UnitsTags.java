package com.zinzin.autochessguide.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnitsTags {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bonus")
    @Expose
    private List<String> bonus = null;

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
}
