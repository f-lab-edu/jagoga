create table roomtype (
    roomtype_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accommodation_id BIGINT not null,
    name varchar(50),
    description varchar(255),
    price INT,
    created_at DATETIME,
    modified_at DATETIME,
    FOREIGN KEY (accommodation_id)
    REFERENCES accommodation (accommodation_id) ON DELETE CASCADE
) engine=InnoDB default character set = utf8;;