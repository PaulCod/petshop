CREATE TABLE IF NOT EXISTS tutor(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) not null,
    phone char(11) not null,
    email varchar(50) not null unique
)