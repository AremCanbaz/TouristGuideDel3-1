package com.example.touristguidedel31.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.example.touristguidedel31.model.TouristAttraction;


import java.sql.*;
import java.util.*;

@Repository
public class TouristRepository {
    @Value("jdbc:mysql://touristguide3-1.mysql.database.azure.com:3306/tourist")
    private String databaseURL;
    @Value("AC25")
    private String username;
    @Value("Konto2500!")
    private String password;

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private final ArrayList<String> cityNames = new ArrayList<>(Arrays.asList("København", "Aarhus", "Odense", "Aalborg", "Esbjerg", "Randers", "Kolding", "Horsens", "Vejle", "Roskilde"));


    public void touristRepository() {
        String sql = "SELECT ta.Name, ta.Description, ta.District, tt.TagName\n" +
                "FROM touristattraktioner ta\n" +
                "JOIN attractiontags at ON ta.id = at.AttractionID\n" +
                "JOIN touristtags tt ON tt.TagID = at.TagID;";
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String tagName = resultSet.getString("tagName");
                List<String> tagNames = Arrays.asList(tagName.split(","));
                attractions.add(new TouristAttraction(
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("District"),
                        tagNames
                ));
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Set<String> getAllTags() {
        String sql = "SELECT TagName FROM touristtags";
        Set<String> tags = new HashSet<>();

        // Kontrollér databaseforbindelsen
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Håndter ResultSet
            while (resultSet.next()) {
                String tag = resultSet.getString("TagName"); // Bruger kolonnenavnet
                if (tag != null) { // Tjek for null-værdier
                    tags.add(tag);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace(); // For mere detaljeret fejlinformation
        }
        return tags;
    }
    public Set<String> getAllDistricts() {
        Set<String> district = new HashSet<>();
        String sql = "SELECT District FROM touristattraktioner";
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String district1 = resultSet.getString("District");
                if (district1 != null) {
                    district.add(district1);
                }
            }

            }catch (SQLException e){
            System.err.println("Database error: " + e.getMessage());
        }
            return district;
        }
    public Set<String> getAllDescription() {
        Set<String> descriptions = new HashSet<>();
        for (TouristAttraction attraction : attractions) {
            descriptions.add(attraction.getDistrict());
        }
        return descriptions;
    }

    // manipulate list
    public ArrayList<TouristAttraction> getAllAttractions() {
        touristRepository();
        return new ArrayList<>(attractions);
    }


    public TouristAttraction getAttractionByName(String name) {
        return attractions.stream()
                .filter(attraction -> attraction.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
    }
    // update
    public TouristAttraction updateAttraction(TouristAttraction updatedAttraction) {
        for (int i = 0; i < attractions.size(); i++) {
            TouristAttraction currentAttraction = attractions.get(i);
            if (currentAttraction.getName().equalsIgnoreCase(updatedAttraction.getName())) {
                attractions.set(i, updatedAttraction); // Erstat gammel attraktion med opdateret attraktion
                return updatedAttraction;
            }
        }
        return null;
    }

    public void deleteAttraction(String name) {
        attractions.removeIf(attraction -> attraction.getName().equalsIgnoreCase(name));
    }

}