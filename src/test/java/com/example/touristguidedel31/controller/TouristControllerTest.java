package com.example.touristguidedel31.controller;

import com.example.touristguidedel31.model.TouristAttraction;
import com.example.touristguidedel31.repository.TouristRepository;
import com.example.touristguidedel31.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristRepository touristRepository;
    @MockBean
    private TouristService touristService;


    @Test
    void showAttractions() throws Exception {
        List<TouristAttraction> mockAttractions = List.of(
                new TouristAttraction("Tivoli", "Amusement park in Copenhagen", List.of("Vesterbro") ,List.of("Family", "Entertainment")));
        new TouristAttraction("Statens Museum for Kunst", "Modern art museum", List.of("Østerbro") ,List.of("Art", "Culture"));

        given(touristService.getAllAttractions()).willReturn(mockAttractions);

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionsList"))
                .andExpect(model().attribute("attractions", mockAttractions));

    }

    @Test
    void createAttraction() throws Exception {
        Set<String> mockTags = Set.of("Family", "Shopping", "Sightseeing");
        Set<String> mockTowns = Set.of("Vesterbro", "Østerbro", "Horsens");

        // Mock opførsel af repository-metoderne
        given(TouristRepository.getAllDistrict()).willReturn(mockTowns);
        given(TouristRepository.getAllTags()).willReturn(mockTags);

        // Simuler GET-anmodning til /createAttraction
        mockMvc.perform(get("/createAttraction"))
                .andExpect(status().isOk()) // Forvent status 200 OK
                .andExpect(view().name("createAttraction")) // Forvent at view-navnet er createAttraction
                .andExpect(model().attribute("descriptions", mockTowns)) // Forvent at model indeholder "descriptions"
                .andExpect(model().attribute("tags", mockTags)); // Forvent at model indeholder "tags"
    }

    @Test
    void addAttraction() {
    }

    @Test
    void showAttractionDetails() {
    }

    @Test
    void updateAttraction() {
    }

    @Test
    void testUpdateAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

}