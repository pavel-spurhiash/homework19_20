package com.gmail.pashasimonpure.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GenericRepository<T> {

    Connection getConnection() throws SQLException;

}