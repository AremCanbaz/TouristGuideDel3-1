package com.example.touristguidedel31.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.touristguidedel31.model.TouristAttraction;
import com.example.touristguidedel31.repository.*;
import com.example.touristguidedel31.service.*;

import java.util.List;
import java.util.Set;

@Controller
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService, TouristRepository touristRepository) {
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
        Set<String> allTowns = TouristRepository.getAllDistrict(); // Brug instansen af touristRepository
        Set<String> allTags = TouristRepository.getAllTags();
        model.addAttribute("descriptions", allTowns);
        model.addAttribute("tags", allTags);
        return "createAttraction"; // Returner view-navnet
    }

    @PostMapping("/addAttraction")
    public String addAttraction(@RequestParam("navn") String name,
                                @RequestParam("beskrivelse") String description,
                                @RequestParam("description") List<String> town,
                                @RequestParam("tags") List<String> tags) {
        // Opret en ny attraktion baseret på formularens data
        TouristAttraction newAttraction = new TouristAttraction(name,description,town,tags);
        newAttraction.setName(name);
        newAttraction.setDescription(description);
        newAttraction.setDistrict(town);
        newAttraction.setTags(tags); // Sæt de valgte tags

        // Gem attraktionen ved hjælp af touristService
        touristService.addAttraction(newAttraction);

        // Redirect til en side, f.eks. listen over attraktioner
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
        Set<String> allTags = TouristService.getAllTags();
        Set<String> allDistricts = TouristService.getAllDistricts();
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