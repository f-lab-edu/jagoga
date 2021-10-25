create table booking (
    booking_id bigint AUTO_INCREMENT not null,
    user_id bigint not null,
    roomtype_id bigint not null,
    check_in_date date,
    check_out_date date,
    booking_status varchar(20),
    created_at datetime(6),
    modified_at datetime(6),
    primary key (booking_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (roomtype_id) REFERENCES roomtype (roomtype_id)
) engine=InnoDB;