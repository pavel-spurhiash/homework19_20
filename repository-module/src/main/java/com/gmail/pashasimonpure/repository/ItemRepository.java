package com.gmail.pashasimonpure.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.pashasimonpure.model.Item;

public interface ItemRepository extends GenericRepository<Item> {

    Item add(Connection connection, Item item) throws SQLException;

    List<Item> findAll(Connection connection) throws SQLException;

    List<Item> findAllCompleted(Connection connection) throws SQLException;

    void updateStatusById(Connection connection, Long id, String newStatus) throws SQLException;

    void deleteAllByStatus(Connection connection, String status) throws SQLException;

}