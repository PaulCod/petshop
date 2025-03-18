package org.example.app.persistence.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AgendamentoEntity {
    private Long id;
    private ServiceEntity serviceEntity;
    private CachorroEntity cachorroEntity;
    private String status;
    private LocalDateTime date;
}
