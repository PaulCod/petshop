package org.example.app.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceEntity {
    private Long id;
    private String name;
    private BigDecimal price;
}
