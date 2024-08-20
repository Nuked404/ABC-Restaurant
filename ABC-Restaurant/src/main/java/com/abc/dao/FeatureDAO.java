package com.abc.dao;

import com.abc.model.Feature;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeatureDAO {
    
    public void addFeature(Feature feature) {
        String query = "INSERT INTO feature (title, description, image_path) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, feature.getTitle());
            statement.setString(2, feature.getDescription());
            statement.setString(3, feature.getImagePath());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Feature getFeatureById(int id) {
        Feature feature = null;
        String query = "SELECT * FROM feature WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                feature = new Feature(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return feature;
    }

    public List<Feature> searchFeatures(String keyword) {
        List<Feature> features = new ArrayList<>();
        String query = "SELECT * FROM feature WHERE CONCAT(title, ' ', description) LIKE ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                features.add(new Feature(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return features;
    }

    public void updateFeature(Feature feature) {
        String query = "UPDATE feature SET title = ?, description = ?, image_path = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, feature.getTitle());
            statement.setString(2, feature.getDescription());
            statement.setString(3, feature.getImagePath());
            statement.setInt(4, feature.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFeature(int id) {
        String query = "DELETE FROM feature WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
