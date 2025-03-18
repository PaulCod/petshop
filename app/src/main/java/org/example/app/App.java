/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example.app;

import org.apache.commons.text.WordUtils;
import org.example.app.persistence.migration.MigrationStrategy;

import java.sql.SQLException;

import static org.example.app.persistence.config.ConnectionConfig.getConnection;

public class App {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()) {
            if (connection != null) {
                new MigrationStrategy(connection).executeMigration();
            } else {
                System.out.println("Nao esta funcionando");
            }

        }

    }
}
