create table USERS (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    name VARCHAR(30),
    password VARCHAR(30),
    phone VARCHAR(20),
    role VARCHAR(10),
    created_at DATETIME,
    updated_at DATETIME,
    UNIQUE INDEX (email)
) engine=InnoDB default character set = utf8;