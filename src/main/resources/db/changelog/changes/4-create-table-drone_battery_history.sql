--liquibase formatted sql
--changeset danilgugu:4

set search_path to drones_schema;

create sequence drone_battery_history_id_seq increment by 50;

create table drone_battery_history
(
    id            bigint    not null primary key default nextval('drone_battery_history_id_seq'),
    drone_id      bigint    not null references drone (id),
    battery_level integer   not null,
    date_time     timestamp not null
);
alter sequence drone_battery_history_id_seq owned by drone_battery_history.id;
