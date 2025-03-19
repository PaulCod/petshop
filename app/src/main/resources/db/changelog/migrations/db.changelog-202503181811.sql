CREATE TABLE IF NOT EXISTS agendamento(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_service BIGINT NOT NULL,
    id_cachorro BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_agendamento TIMESTAMP NOT NULL
);