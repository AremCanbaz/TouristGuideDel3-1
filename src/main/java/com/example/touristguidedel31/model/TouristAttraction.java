package com.example.touristguidedel31.model;

import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private String district;
    private List<String> tags;

    public TouristAttraction(String name, String description, String district, List<String> tags) {
        this.name = name;
        this.description = description;
        this.district = district;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public  String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
}