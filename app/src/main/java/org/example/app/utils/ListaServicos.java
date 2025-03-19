package org.example.app.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.app.persistence.entity.ServiceEntity;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class ListaServicos {
    private List<Servico> servicos = List.of(
            new Servico("Banho", new BigDecimal("20.00")),
            new Servico("Tosa", new BigDecimal("30.00")),
            new Servico("Consulta", new BigDecimal("200.00")),
            new Servico("Vacina", new BigDecimal("350.00"))
    );

    public List<Servico> getServicos() {
        return servicos;
    }

}
