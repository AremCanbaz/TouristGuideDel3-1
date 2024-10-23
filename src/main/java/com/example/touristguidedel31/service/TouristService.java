package com.example.touristguidedel31.service;

import com.example.touristguidedel31.model.TouristAttraction;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.touristguidedel31.repository.TouristRepository;

import java.util.*;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public Set<String> getAllTags() {
        return touristRepository.getAllTags();
    }

    // Get all attractions
    public Set<TouristAttraction> getAllAttractions() {
        return touristRepository.getAttractionsSet();
    }
    public Set<TouristAttraction> getAllAttractionSet(){
        return touristRepository.getAttractionsSet();
    }

    public Set<String> getAllDistricts() {
        return touristRepository.getAllDistricts();
    }

    // Get an attraction by name
    public TouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    // Add a new attraction
    public void addAttraction(TouristAttraction attraction) {
        touristRepository.addAttraction(attraction);
    }

    // Update an existing attraction
    public void updateAttraction(TouristAttraction attraction) {
        touristRepository.updateAttraction(attraction);
    }

    // Delete an attraction by name
    public void deleteAttraction(String name) {
        touristRepository.deleteAttraction(name);
    }

    public Set<String> getDescription() {
        return touristRepository.getAllDescription();
    }
}
