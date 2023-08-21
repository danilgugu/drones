--liquibase formatted sql
--changeset danilgugu:1

set search_path to drones_schema;

create sequence drone_id_seq increment by 50;

create table drone
(
    id               bigint  not null primary key default nextval('drone_id_seq'),
    serial_number    varchar not null unique,
    model            varchar,
    weight_limit     integer,
    battery_capacity integer not null,
    state            varchar not null
);
alter sequence drone_id_seq owned by drone.id;
