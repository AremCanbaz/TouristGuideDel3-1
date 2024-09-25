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


    // Jeg ved ikke hvorfor den her bliver ved med at fejle...
    @Test
    void createAttraction() throws Exception {
        Set<String> mockTags = Set.of("Family", "Shopping", "Sightseeing");
        Set<String> mockTowns = Set.of("Vesterbro", "Østerbro", "Horsens");

        // Mock opførsel af repository-metoderne
        given(TouristService.getAllDistricts()).willReturn(mockTowns);
        given(TouristService.getAllTags()).willReturn(mockTags);

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
    void showAttractionDetails() throws Exception {
        String mockAttractionname = "Tivoli";
        TouristAttraction mockAttraction = new TouristAttraction("Tivoli","Et sted i kbh", List.of("Vesterbro") ,List.of("Family", "Entertainment"));
        given(touristService.getAttractionByName(mockAttractionname)).willReturn(mockAttraction);

        mockMvc.perform(get("/tags/{name}", mockAttractionname))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attribute("attraction",mockAttraction));

    }

    @Test
    void updateAttraction() throws Exception {
        String attractionName = "Tivoli";
        TouristAttraction mockAttraction = new TouristAttraction(attractionName, "Amusement park in Copenhagen", List.of("Vesterbro"), List.of("Family", "Entertainment"));

        Set<String> mockTags = Set.of("Family", "Shopping", "Sightseeing");
        Set<String> mockDistricts = Set.of("Vesterbro", "Østerbro", "Horsens");

        // Stub metoderne for at returnere mock data
        given(touristService.getAttractionByName(attractionName)).willReturn(mockAttraction);
        given(TouristService.getAllTags()).willReturn(mockTags);
        given(TouristService.getAllDistricts()).willReturn(mockDistricts);

        // Simuler GET-anmodning til den rigtige URL
        mockMvc.perform(get("/update/{name}", attractionName)) // Brug den korrekte URL-sti
                .andExpect(status().isOk()) // Forvent HTTP 200 OK
                .andExpect(view().name("update-attraction")) // Forvent at view-navnet er update-attraction
                .andExpect(model().attribute("allTags", mockTags)) // Forvent at modellen indeholder allTags
                .andExpect(model().attribute("allTowns", mockDistricts)) // Forvent at modellen indeholder allTowns
                .andExpect(model().attribute("attraction", mockAttraction)); // Forvent at modellen indeholder mockAttraction
    }

    @Test
    void testUpdateAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

}