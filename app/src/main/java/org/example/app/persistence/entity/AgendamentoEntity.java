package org.example.app.persistence.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
public class AgendamentoEntity {
    private Long id;
    private ServiceEntity serviceEntity;
    private CachorroEntity cachorroEntity;
    private String status;
    private Timestamp date;
}
