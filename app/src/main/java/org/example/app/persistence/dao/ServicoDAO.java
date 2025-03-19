package org.example.app.persistence.dao;

import lombok.AllArgsConstructor;
import org.example.app.persistence.entity.ServiceEntity;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ServicoDAO {
    private final Connection connection;

    public void insert(final ServiceEntity entity) throws SQLException {
        var sql = "INSERT INTO service(name, PRICE) values (?, ?);";
        try(var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            var i = 1;
            statement.setString(i ++, entity.getName());
            statement.setBigDecimal(i, entity.getPrice());
            statement.executeUpdate();
        }
    }

    public boolean existsByName(final String name) throws SQLException {
        var sql = "SELECT 1 from service where name = ?;";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try(var resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public List<ServiceEntity> getAllServices() throws SQLException {
        List<ServiceEntity> services = new ArrayList<>();
        var sql = "select id, name, PRICE from service";
        try(var statement = connection.prepareStatement(sql)) {
            try (var resultSet = statement.executeQuery()){
                while(resultSet.next()) {
                    ServiceEntity service = new ServiceEntity();
                    service.setId(resultSet.getLong("id"));
                    service.setName(resultSet.getString("name"));
                    service.setPrice(resultSet.getBigDecimal("PRICE"));
                    services.add(service);
                }
            }
        }
        return services;
    }
}
