set search_path to drones_schema;

delete
from drone
where serial_number = '-1';
