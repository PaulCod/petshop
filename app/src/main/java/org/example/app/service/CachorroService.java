package org.example.app.service;

import lombok.AllArgsConstructor;
import org.example.app.persistence.dao.CachorroDAO;
import org.example.app.persistence.entity.CachorroEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class CachorroService {

    private final Connection connection;

    public CachorroEntity create(final CachorroEntity entity, final Long id) throws SQLException {
        try {
            var dao = new CachorroDAO(connection);

            var entitySaved = dao.insert(entity, id);
            connection.commit();
            return entitySaved;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public boolean delete(final Long id, final Long tutorId) throws SQLException {
        try{
            var dao = new CachorroDAO(connection);
            if(!dao.exists(id, tutorId)) {
                return false;
            }
            dao.delete(id, tutorId);
            connection.commit();
            return true;
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        }
    }

    public List<CachorroEntity> findAllByTutorId(final Long id) throws SQLException {
        try {
            var dao = new CachorroDAO(connection);
            return dao.findAllByUserId(id);
        } catch (SQLException ex) {
            throw ex;
        }
    }
}
