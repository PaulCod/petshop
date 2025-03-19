package org.example.app.persistence.dao;

import lombok.AllArgsConstructor;
import org.example.app.persistence.entity.CachorroEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CachorroDAO {
    private final Connection connection;

    public CachorroEntity insert(final CachorroEntity entity, final Long tutorId) throws SQLException {
        var sql = "INSERT INTO cachorro(name, age, tutor_id) VALUES (?, ?, ?)";

        try(var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            var i = 1;
            statement.setString(i ++, entity.getName());
            statement.setInt(i ++, entity.getAge());
            statement.setLong(i, tutorId);
            statement.executeQuery();
            try(var resultSet = statement.getGeneratedKeys()){
                if(resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        }
        return entity;
    }

    public void delete(final Long id) throws SQLException {
        var sql = "DELETE FROM cachorro where id = ?";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
        }
    }

    public List<CachorroEntity> findAllByUserId(final Long tutorId) throws SQLException{
        List<CachorroEntity> cachorros = new ArrayList<>();
        var sql = "SELECT id, name, age FROM cachorro WHERE tutor_id = ?";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, tutorId);
            try (var resultSet = statement.executeQuery()){
                while(resultSet.next()) {
                    CachorroEntity cachorro = new CachorroEntity();
                    cachorro.setId(resultSet.getLong("id"));
                    cachorro.setName(resultSet.getString("name"));
                    cachorro.setAge(resultSet.getInt("age"));
                    cachorros.add(cachorro);
                }
            }
        }
        return cachorros;
    }
}
