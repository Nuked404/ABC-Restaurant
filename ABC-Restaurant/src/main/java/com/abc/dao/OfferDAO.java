package com.abc.dao;

import com.abc.model.Offer;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    // Add a new offer to the database
    public void addOffer(Offer offer) {
        String query = "INSERT INTO offer (title, description, image_path) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, offer.getTitle());
            statement.setString(2, offer.getDescription());
            statement.setString(3, offer.getImagePath());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve an offer by its ID
    public Offer getOfferById(int id) {
        Offer offer = null;
        String query = "SELECT * FROM offer WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                offer = new Offer(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offer;
    }

    // Retrieve all offers from the database
    public List<Offer> getAllOffers() {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM offer";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                offers.add(new Offer(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offers;
    }

    // Update an existing offer in the database
    public void updateOffer(Offer offer) {
        String query = "UPDATE offer SET title = ?, description = ?, image_path = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, offer.getTitle());
            statement.setString(2, offer.getDescription());
            statement.setString(3, offer.getImagePath());
            statement.setInt(4, offer.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an offer from the database
    public void deleteOffer(int id) {
        String query = "DELETE FROM offer WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search offers based on title and description
    public List<Offer> searchOffers(String keyword) {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM offer WHERE CONCAT(title, ' ', description) LIKE ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                offers.add(new Offer(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offers;
    }
}
