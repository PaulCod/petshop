package org.example.app.persistence.entity;

import lombok.Data;

@Data
public class TutorEntity {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
