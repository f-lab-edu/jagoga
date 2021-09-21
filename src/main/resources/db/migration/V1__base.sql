create table users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(10) NOT NULL,
    created_at DATETIME,
    modified_at DATETIME,
    UNIQUE INDEX ux_email (email)
) engine=InnoDB default character set = utf8;

create table accommodation (
    accommodation_id bigint not null,
    created_at datetime(6),
    modified_at datetime(6),
    accommodation_name varchar(255),
    accommodation_type varchar(255),
    description varchar(255),
    information varchar(255),
    low_price integer not null,
    owner_id bigint,
    phone_number varchar(255),
    city_id bigint,
    primary key (accommodation_id)
) engine=InnoDB;

create table category (
    category_id bigint not null,
    name varchar(255),
    primary key (category_id)
) engine=InnoDB;

create table city (
    city_id bigint not null,
    name varchar(255),
    category_id bigint,
    state_id bigint,
    primary key (city_id)
) engine=InnoDB;

create table state (
    state_id bigint not null,
    name varchar(255),
    primary key (state_id)
) engine=InnoDB;