package org.example.app.persistence.entity;

import lombok.Data;

@Data
public class CachorroEntity {
    private Long id;
    private String name;
    private int age;
    private TutorEntity tutorEntity;
}
