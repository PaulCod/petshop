package org.example.app.service;

import lombok.AllArgsConstructor;
import org.example.app.persistence.dao.AgendamentoDAO;
import org.example.app.persistence.entity.AgendamentoEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class AgendamentoService {
    private Connection connection;

    public AgendamentoEntity create(final AgendamentoEntity entity) throws SQLException {
        try {
            var dao = new AgendamentoDAO(connection);
            var agendamento = dao.insert(entity);
            connection.commit();
            return agendamento;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public List<AgendamentoEntity> getAllByTutorId(final Long id) throws SQLException {
        try {
            var dao = new AgendamentoDAO(connection);
            return dao.getAllByTutorId(id);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public boolean delete(final Long id) throws SQLException {
        try {
            var dao = new AgendamentoDAO(connection);
            if(!dao.existsById(id)) {
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

}
