create table room_inventory (
    roominventory_id bigint AUTO_INCREMENT not null,
    roomtype_id bigint not null,
    inventory_date datetime(6),
    available_count int,
    created_at datetime(6),
    modified_at datetime(6),
    primary key (roominventory_id),
    FOREIGN KEY (roomtype_id) REFERENCES roomtype (roomtype_id) ON DELETE CASCADE
) engine=InnoDB;

alter table roomtype add owner_id bigint;