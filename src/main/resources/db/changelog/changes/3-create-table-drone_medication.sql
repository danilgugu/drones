--liquibase formatted sql
--changeset danilgugu:3

set search_path to drones_schema;

create table drone_medication
(
    drone_id      bigint not null references drone (id),
    medication_id bigint not null unique references medication (id),
    primary key (drone_id, medication_id)
);
