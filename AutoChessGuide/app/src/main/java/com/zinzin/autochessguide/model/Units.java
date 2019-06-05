package com.zinzin.autochessguide.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Units {
    private int full_image;
    private int icon_image;
    private int mini_image;
    private int skill_image;
    private int color_name;
    private boolean isClick = false;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dota_convert")
    @Expose
    private String dotaConvert;
    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("race")
    @Expose
    private List<String> race = null;
    @SerializedName("dota_convert_r")
    @Expose
    private List<String> dotaConvertR = null;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("dota_convert_c")
    @Expose
    private String dotaConvertC;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("health")
    @Expose
    private List<String> health = null;
    @SerializedName("armor")
    @Expose
    private List<String> armor = null;
    @SerializedName("resistance")
    @Expose
    private List<String> resistance = null;
    @SerializedName("attack")
    @Expose
    private List<String> attack = null;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("dps")
    @Expose
    private List<Float> dps = null;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("skill")
    @Expose
    private List<UnitsSkills> skill = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDotaConvert() {
        return dotaConvert;
    }

    public void setDotaConvert(String dotaConvert) {
        this.dotaConvert = dotaConvert;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getRace() {
        return race;
    }

    public void setRace(List<String> race) {
        this.race = race;
    }

    public List<String> getDotaConvertR() {
        return dotaConvertR;
    }

    public void setDotaConvertR(List<String> dotaConvertR) {
        this.dotaConvertR = dotaConvertR;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getDotaConvertC() {
        return dotaConvertC;
    }

    public void setDotaConvertC(String dotaConvertC) {
        this.dotaConvertC = dotaConvertC;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<String> getHealth() {
        return health;
    }

    public void setHealth(List<String> health) {
        this.health = health;
    }

    public List<String> getArmor() {
        return armor;
    }

    public void setArmor(List<String> armor) {
        this.armor = armor;
    }

    public List<String> getResistance() {
        return resistance;
    }

    public void setResistance(List<String> resistance) {
        this.resistance = resistance;
    }

    public List<String> getAttack() {
        return attack;
    }

    public void setAttack(List<String> attack) {
        this.attack = attack;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public List<Float> getDps() {
        return dps;
    }

    public void setDps(List<Float> dps) {
        this.dps = dps;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<UnitsSkills> getSkill() {
        return skill;
    }

    public void setSkill(List<UnitsSkills> skill) {
        this.skill = skill;
    }

    public int getFull_image() {
        return full_image;
    }

    public void setFull_image(int full_image) {
        this.full_image = full_image;
    }

    public int getIcon_image() {
        return icon_image;
    }

    public void setIcon_image(int icon_image) {
        this.icon_image = icon_image;
    }

    public int getMini_image() {
        return mini_image;
    }

    public void setMini_image(int mini_image) {
        this.mini_image = mini_image;
    }

    public int getSkill_image() {
        return skill_image;
    }

    public void setSkill_image(int skill_image) {
        this.skill_image = skill_image;
    }

    public int getColor_name() {
        return color_name;
    }

    public void setColor_name(int color_name) {
        this.color_name = color_name;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
