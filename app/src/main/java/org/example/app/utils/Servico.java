package org.example.app.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Servico {
    private String name;
    private BigDecimal price;
}
