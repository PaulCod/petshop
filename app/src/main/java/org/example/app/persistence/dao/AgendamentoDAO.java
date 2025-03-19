package org.example.app.persistence.dao;

import lombok.AllArgsConstructor;
import org.example.app.persistence.entity.AgendamentoEntity;
import org.example.app.persistence.entity.CachorroEntity;
import org.example.app.persistence.entity.ServiceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AgendamentoDAO {
    private Connection connection;

    public AgendamentoEntity insert(AgendamentoEntity entity) throws SQLException {
        var sql = "insert into agendamento(id_cachorro, id_service, status, data_agendamento) values (?, ?, ?, ?);";
        try(var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getCachorroEntity().getId());
            statement.setLong(2, entity.getServiceEntity().getId());
            statement.setString(3, entity.getStatus());
            statement.setTimestamp(4, entity.getDate());

            statement.executeUpdate();

            try(var resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        }

        return entity;
    }

    public List<AgendamentoEntity> getAllByTutorId(final Long id) throws SQLException {
        List<AgendamentoEntity> list = new ArrayList<>();
        var sql = """
                select 
                a.id as agendamento_id, 
                d.name as dog_name,
                d.id as dog_id,
                d.age as dog_age,
                d.tutor_id as dog_tutor,
                s.id as service_id ,
                s.name as service_name, 
                s.price as service_price, 
                a.status as agendamento_status, 
                a.data_agendamento as data_agendamento from agendamento a 
                inner join service s on a.id_service = s.id 
                inner join cachorro d on  a.id_cachorro = d.id
                where d.tutor_id = ?;
                """;
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try(var resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    CachorroEntity cachorro = new CachorroEntity();
                    cachorro.setId(resultSet.getLong("dog_id"));
                    cachorro.setName(resultSet.getString("dog_name"));
                    cachorro.setAge(resultSet.getInt("dog_age"));

                    ServiceEntity service = new ServiceEntity();
                    service.setId(resultSet.getLong("service_id"));
                    service.setName(resultSet.getString("service_name"));
                    service.setPrice(resultSet.getBigDecimal("service_price"));

                    AgendamentoEntity agendamento = new AgendamentoEntity();
                    agendamento.setId(resultSet.getLong("agendamento_id"));
                    agendamento.setDate(resultSet.getTimestamp("data_agendamento"));
                    agendamento.setStatus(resultSet.getString("agendamento_status"));
                    agendamento.setServiceEntity(service);
                    agendamento.setCachorroEntity(cachorro);

                    list.add(agendamento);
                }
            }
            return list;
        }
    }

    public boolean existsById(final Long id) throws SQLException {
        var sql = "Select 1 from agendamento where id = ?;";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()){
                return resultSet.next();
            }
        }
    }

    public void delete(final Long id) throws SQLException {
        var sql = "Delete from agendamento where id = ?;";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int rows = statement.executeUpdate();
            System.out.println("Rows" + rows + " " + id);
        }
    }
}
