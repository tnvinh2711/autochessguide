package com.zinzin.autochessguide.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnitsSkills {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dota_convert")
    @Expose
    private String dotaConvert;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tags")
    @Expose
    private List<UnitsTags> tags = null;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UnitsTags> getTags() {
        return tags;
    }

    public void setTags(List<UnitsTags> tags) {
        this.tags = tags;
    }
}
