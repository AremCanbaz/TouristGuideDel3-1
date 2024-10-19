package com.example.touristguidedel31.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TouristAttraction {
    private String name;
    private String description;
    private String district;
    private Set<String> tags;

    public TouristAttraction(String name, String description, String district, Set<String> tags) {
        this.name = name;
        this.description = description;
        this.district = district;
        this.tags = new HashSet<>(tags);
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



    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
    public  String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TouristAttraction that = (TouristAttraction) o;
    return Objects.equals(name, that.name) && Objects.equals(district, that.district);
}
    public int hashCode() {
        return Objects.hash(name, district); // Brug navn og district som unikt identifikator
    }
    public void addTag(List<String> newTag) {
        this.tags.addAll(newTag);
    }
}