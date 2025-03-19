package org.example.app.service;

import lombok.AllArgsConstructor;
import org.example.app.persistence.dao.ServicoDAO;
import org.example.app.persistence.entity.ServiceEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class ServiceService {
    private final Connection connection;

    public void create(final ServiceEntity serviceEntity) throws SQLException {
        try {
            var dao = new ServicoDAO(connection);

            dao.insert(serviceEntity);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public List<ServiceEntity> getAllServices() throws SQLException {
        try {
            var dao = new ServicoDAO(connection);
            return dao.getAllServices();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public boolean existsByName(final String name) throws SQLException {
        try {
            var dao = new ServicoDAO(connection);
            return dao.existsByName(name);
        } catch (SQLException ex) {
            throw ex;
        }
    }
}
