CREATE TABLE IF NOT EXISTS cachorro(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    age INT NOT NULL,
    tutor_id BIGINT NOT NULL,
    CONSTRAINT tutor_dog_fk FOREIGN KEY(tutor_id) REFERENCES tutor(id) ON DELETE CASCADE;
);