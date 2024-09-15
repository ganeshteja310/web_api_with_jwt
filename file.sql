CREATE TABLE employ (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    phone_number BIGINT,
    address JSONB );

INSERT INTO employ (id, name, phone_number, address) 
VALUES (1, 'ramesh', 1234567890, '{"street": "area-1", "city": "banglore", "zip": "10001"}');

INSERT INTO employ (id, name, phone_number, address) 
VALUES (2, 'ganesh', 9876543210, '{"street": "area-2", "city": "Hyderabad", "zip": "94105"}');

INSERT INTO employ (id, name, phone_number, address) 
VALUES (3, 'suresh', 5551234567, '{"street": "area-3", "city": "Chennai", "zip": "60610"}');


CREATE TABLE workers (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    phone_number BIGINT,
    address JSONB );

INSERT INTO workers (id, name, phone_number, address) 
VALUES (1, 'mukesh', 1212121212, '{"street": "st-1", "city": "Banglore", "zip": "90001"}');

INSERT INTO workers (id, name, phone_number, address) 
VALUES (2, 'rajesh', 3434343434, '{"street": "st-2", "city": "Tirupati", "zip": "33101"}');

INSERT INTO workers (id, name, phone_number, address) 
VALUES (3, 'vikesh', 6767676767, '{"street": "st-3", "city": "mumbaai", "zip": "73301"}');


CREATE TABLE departments (
    department_id INTEGER PRIMARY KEY,
    department_name TEXT NOT NULL,
    facilities TEXT,
    transport BOOLEAN,
    food BOOLEAN
);

INSERT INTO departments (department_id, department_name, facilities, transport, food) 
VALUES (1, 'Engineering', 'Gym, Library, Conference', TRUE, TRUE);

INSERT INTO departments (department_id, department_name, facilities, transport, food) 
VALUES (2, 'Design', 'Studio, Lab', FALSE, TRUE);

INSERT INTO departments (department_id, department_name, facilities, transport, food) 
VALUES (3, 'HR', ' Lounge,Rooms', TRUE, FALSE);
