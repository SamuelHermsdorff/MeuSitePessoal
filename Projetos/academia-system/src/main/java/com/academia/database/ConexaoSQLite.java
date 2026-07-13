package com.academia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static final String URL =
            "jdbc:sqlite:academia.db";

    public static Connection conectar() {

        try {

            return DriverManager.getConnection(URL);

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao conectar com SQLite",
                    e
            );
        }
    }
}