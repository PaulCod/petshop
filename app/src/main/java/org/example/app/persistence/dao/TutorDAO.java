package org.example.app.persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;
import org.example.app.persistence.entity.TutorEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class TutorDAO {
    private final Connection connection;

    public TutorEntity insert(final TutorEntity tutorEntity) throws SQLException {
        var sql = "INSERT INTO tutor(name, email, phone) VALUES (?, ?, ?);";
        try(var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            var i = 1;
            statement.setString(i ++,tutorEntity.getName());
            statement.setString(i ++, tutorEntity.getEmail());
            statement.setString(i, tutorEntity.getPhoneNumber());

            statement.executeUpdate();

            try(var resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    tutorEntity.setId(resultSet.getLong(1));
                }
            }
        }
        return tutorEntity;
    }

    public void delete(final Long id) throws SQLException {
        var sql = "DELETE FROM tutor WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<TutorEntity> findByEmail(final String email) throws SQLException{
        var sql = "SELECT * FROM tutor WHERE email = ?;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if(resultSet.next()) {
                var tutorEntity = new TutorEntity();
                tutorEntity.setId(resultSet.getLong("id"));
                tutorEntity.setName(resultSet.getString("name"));
                tutorEntity.setEmail(resultSet.getString("email"));
                tutorEntity.setPhoneNumber(resultSet.getString("phone"));
                return Optional.of(tutorEntity);
            }
            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException {
        var sql = "SELECT 1 FROM tutor WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }

    public boolean existsByEmail(final String email) throws SQLException {
        var sql = "SELECT 1 FROM tutor WHERE email = ?;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }
}
