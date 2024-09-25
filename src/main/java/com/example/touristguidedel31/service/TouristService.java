package com.example.touristguidedel31.service;

import com.example.touristguidedel31.model.TouristAttraction;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.touristguidedel31.repository.TouristRepository;



import java.util.*;

@Service
public class TouristService {

    private static TouristRepository touristRepository = new TouristRepository();

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        TouristService.touristRepository = touristRepository;
    }

    public static Set<String> getAllTags() {
        return TouristRepository.getAllTags();
    }

    // Get all attractions
    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAllAttractions();
    }

    public static Set<String> getAllDistricts() {
        return TouristRepository.getAllDistricts();
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

    public Set<String> getTags() {
        return TouristRepository.getAllTags();

    }
    public Set<String> getDescription() {
        return touristRepository.getAllDescription();
    }
}