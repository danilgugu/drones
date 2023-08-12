create table drone
(
    id               bigint  not null primary key auto_increment,
    serial_number    varchar not null unique,
    model            varchar,
    weight_limit     integer,
    battery_capacity integer not null,
    state            varchar not null
);

create table medication
(
    id     bigint  not null primary key auto_increment,
    name   varchar not null,
    weight integer not null,
    code   varchar,
    _image binary large object not null
);

create table drone_medication
(
    drone_id      bigint not null references drone (id),
    medication_id bigint not null unique references medication (id),
    primary key (drone_id, medication_id)
);

create table drone_battery_history
(
    id            bigint    not null primary key auto_increment,
    drone_id      bigint    not null references drone (id),
    battery_level integer   not null,
    date_time     timestamp not null
);
