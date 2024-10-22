package com.example.touristguidedel31.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.example.touristguidedel31.model.TouristAttraction;


import javax.naming.Name;
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


    public void touristRepository() {
        String sql = "SELECT ta.Name, ta.Description, ta.District, tt.TagName " +
                "FROM touristattraktioner ta " +
                "JOIN attractiontags at ON ta.id = at.AttractionID " +
                "JOIN touristtags tt ON tt.TagID = at.TagID;";
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Brug en HashMap til at sikre, at vi ikke opretter duplikationer af attraktioner
            Map<String, TouristAttraction> attractionMap = new HashMap<>();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                String district = resultSet.getString("District");
                String tagName = resultSet.getString("TagName");

                // Nøgle for at identificere unikke attraktioner (name + district)
                String key = name + district;

                // Hvis attraktionen allerede findes i mappen
                if (attractionMap.containsKey(key)) {
                    TouristAttraction existingAttraction = attractionMap.get(key);
                    // Tilføj det nye tag til den eksisterende attraktion
                    existingAttraction.getTags().add(tagName);
                } else {
                    // Opret en ny attraktion og tilføj det første tag
                    Set<String> tags = new HashSet<>();
                    tags.add(tagName);

                    TouristAttraction newAttraction = new TouristAttraction(name, description, district, tags);
                    attractionMap.put(key, newAttraction);
                }
            }

            // Ryd listen for attraktioner og tilføj kun unikke attraktioner
            attractions.clear();
            attractions.addAll(attractionMap.values());

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Set<TouristAttraction> getAttractionsSet() {
        touristRepository();
        Set<TouristAttraction> touristAttractionsSet = new HashSet<>();
        touristAttractionsSet.addAll(attractions);
        return touristAttractionsSet;
    }
    public Set<String> getAllTags() {
        String sql = "SELECT tag FROM Tags";
        Set<String> tags = new HashSet<>();

        // Kontrollér databaseforbindelsen
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Håndter ResultSet
            while (resultSet.next()) {
                String tag = resultSet.getString("tag"); // Bruger kolonnenavnet
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
        String sql = "SELECT bynavn FROM cities";
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String district1 = resultSet.getString("bynavn");
                if (district1 != null) {
                    district.add(district1);
                }
            }

            }catch (SQLException e){
            System.err.println("Database error: " + e.getMessage());
        }
            return district;
        }
    public void addAttraction(TouristAttraction attraction) {
        ResultSet generatedKeys = null;
        try(Connection connection = DriverManager.getConnection(databaseURL,username,password)){
            String attraktionsql = "INSERT INTO insert into touristattraktioner (Name,Description,District) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(attraktionsql);
            pstmt.setString(1,attraction.getName());
            pstmt.setString(2,attraction.getDescription());
            pstmt.setString(3,attraction.getDistrict());
            pstmt.executeUpdate();

            generatedKeys = pstmt.getGeneratedKeys();
            int attraktionId = 0;
            if (generatedKeys.next()) {
                attraktionId = generatedKeys.getInt(0);
                System.out.println(attraktionId);
            }
            String tagsSql = "select id from tags where tag= ?";
            PreparedStatement tagsStmt = connection.prepareStatement(tagsSql);
            ResultSet rs = tagsStmt.executeQuery();
            String keysSql = "insert into attractiontags(AttractionID,TagID) values(?,?)";
        }
        catch (SQLException e){
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public Set<String> getAllDescription() {
        Set<String> descriptions = new HashSet<>();
        for (TouristAttraction attraction : attractions) {
            descriptions.add(attraction.getDistrict());
        }
        return descriptions;
    }

    // manipulate list


    public TouristAttraction getAttractionByName(String name) {
        return attractions.stream()
                .filter(attraction -> attraction.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
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