package org.example.app.service;

import lombok.AllArgsConstructor;
import org.example.app.persistence.dao.TutorDAO;
import org.example.app.persistence.entity.TutorEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class TutorService {
    private final Connection connection;

    public TutorEntity create(final TutorEntity entity) throws SQLException {
        try {
            var dao = new TutorDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public boolean delete(final Long id) throws SQLException {
        var dao = new TutorDAO(connection);
        try{
            if(!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public Optional<TutorEntity> findByEmail(final String email) throws SQLException {
        var dao = new TutorDAO(connection);
        var optional = dao.findByEmail(email);
        if(optional.isPresent()) {
            var entity = optional.get();
            return Optional.of(entity);
        }
        return Optional.empty();
    }

}
