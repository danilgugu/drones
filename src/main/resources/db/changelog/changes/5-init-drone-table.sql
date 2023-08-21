--liquibase formatted sql
--changeset danilgugu:5

set search_path to drones_schema;

insert into drone (serial_number, model, weight_limit, battery_capacity, state)
values ('1', 'Cruiserweight', 300, 100, 'IDLE'),
       ('2', 'Middleweight', 200, 100, 'IDLE'),
       ('3', 'Heavyweight', 500, 100, 'IDLE'),
       ('4', 'Cruiserweight', 400, 100, 'IDLE'),
       ('5', 'Middleweight', 250, 100, 'IDLE'),
       ('6', 'Lightweight', 100, 100, 'IDLE'),
       ('7', 'Heavyweight', 450, 100, 'IDLE'),
       ('8', 'Cruiserweight', 350, 100, 'IDLE'),
       ('9', 'Lightweight', 150, 100, 'IDLE'),
       ('10', 'Cruiserweight', 380, 100, 'IDLE');
