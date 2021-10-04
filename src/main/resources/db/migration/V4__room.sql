create table room (
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roomtype_id BIGINT not null,
    room_number varchar(50) not null,
    created_at DATETIME,
    modified_at DATETIME,
    FOREIGN KEY (roomtype_id)
    REFERENCES roomtype (roomtype_id) ON DELETE CASCADE
) engine=InnoDB default character set = utf8;;