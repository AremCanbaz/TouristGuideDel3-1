package com.example.touristguidedel31.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.touristguidedel31.model.TouristAttraction;
import com.example.touristguidedel31.service.*;

import java.util.List;
import java.util.Set;

@Controller
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping()
    public String showAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionsList";
    }

    @GetMapping("/createAttraction")
    public String createAttraction(Model model) {
        Set<String> allTowns = touristService.getAllDistricts(); // Brug instansen af touristRepository
        Set<String> allTags = touristService.getAllTags();
        model.addAttribute("district", allTowns);
        model.addAttribute("tags", allTags);
        return "createAttraction"; // Returner view-navnet
    }

    @PostMapping("/addAttraction")
    public String addAttraction(@RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("district") String district,
                                @RequestParam("tags") List<String> tags) {
        // Opret en ny attraktion BASERET PÅ HTML
        TouristAttraction newAttraction = new TouristAttraction(name,description,district,tags);
        newAttraction.setName(name);
        newAttraction.setDescription(description);
        newAttraction.setDistrict(district);
        newAttraction.setTags(tags); // Sæt de valgte tags

        // Gem attraktionen ved hjælp af touristService
        touristService.addAttraction(newAttraction);

        return "redirect:/"; // Redirect til listen over attraktioner
    }
    @GetMapping("/tags/{name}")
    public String showAttractionDetails(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        model.addAttribute("attraction", attraction);  // Use singular 'attraction' for the object
        return "tags";
    }


    @GetMapping("/update/{name}")
    public String updateAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        Set<String> allTags = touristService.getAllTags();
        Set<String> allDistricts = touristService.getAllDistricts();
        model.addAttribute("allTags", allTags);
        model.addAttribute("allTowns", allDistricts);
        model.addAttribute("attraction", attraction);
        return "update-attraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.updateAttraction(attraction);
        return "redirect:/"; // Omdiriger til listen over attraktioner
    }
    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable("name") String name) {
        touristService.deleteAttraction(name);
        return "redirect:/";
    }
}