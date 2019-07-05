package com.zinzin.tierbuilder.model;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    String name_team;
    List<String> hero_name = new ArrayList<>();
    String type;
    List<Units> unitList = new ArrayList<>();

    public Builder(String name_team, List<String> hero_name, String type, List<Units> unitList) {
        this.name_team = name_team;
        this.hero_name = hero_name;
        this.type = type;
        this.unitList = unitList;
    }

    public Builder() {
    }

    public String getName_team() {
        return name_team;
    }

    public void setName_team(String name_team) {
        this.name_team = name_team;
    }

    public List<String> getHero_name() {
        return hero_name;
    }

    public void setHero_name(List<String> hero_name) {
        this.hero_name = hero_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Units> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Units> unitList) {
        this.unitList = unitList;
    }
}
