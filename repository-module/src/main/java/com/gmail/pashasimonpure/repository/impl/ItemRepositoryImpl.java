package com.gmail.pashasimonpure.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.model.Item;
import com.gmail.pashasimonpure.repository.ItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item> implements ItemRepository {

    @Override
    public Item add(Connection connection, Item item) throws SQLException {

        String sql = "INSERT INTO item(name, status) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getStatus());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding item failed, no rows affected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding item failed, no ID obtained");
                }
            }
            return item;
        }
    }

    @Override
    public List<Item> findAll(Connection connection) throws SQLException {

        String sql = "SELECT id, name, status FROM item";

        try (Statement statement = connection.createStatement()) {
            List<Item> items = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    items.add(createItem(resultSet));
                }
                return items;
            }
        }
    }

    @Override
    public List<Item> findAllCompleted(Connection connection) throws SQLException {

        String sql = "SELECT id, name, status FROM item WHERE status = 'COMPLETED'";

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(createItem(resultSet));
                }
                return items;
            }
        }
    }

    @Override
    public void updateStatusById(Connection connection, Long id, String newStatus) throws SQLException {

        String sql = "UPDATE item SET status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("update item failed, no rows affected.");
            }
        }
    }

    @Override
    public void deleteAllByStatus(Connection connection, String status) throws SQLException {

        String sql = "DELETE FROM item WHERE status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("delete item failed, no rows affected.");
            }
        }
    }

    private Item createItem(ResultSet resultSet) throws SQLException{
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setStatus(resultSet.getString("status"));
        return item;
    }

}