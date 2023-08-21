--liquibase formatted sql
--changeset danilgugu:2

set search_path to drones_schema;

create sequence medication_id_seq increment by 50;

create table medication
(
    id     bigint  not null primary key default nextval('medication_id_seq'),
    name   varchar not null,
    weight integer not null,
    code   varchar,
    _image bytea   not null
);
alter sequence medication_id_seq owned by medication.id;
