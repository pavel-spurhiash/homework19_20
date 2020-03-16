package com.gmail.pashasimonpure.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.pashasimonpure.model.User;

public interface UserRepository extends GenericRepository<User> {

    User getUserByUsername(Connection connection, String username) throws SQLException;

}