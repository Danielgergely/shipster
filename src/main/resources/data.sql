INSERT INTO address (id, street, number, city, zip, country) VALUES (1, 'Luterbachstrasse', '29', 'Zuchwil', '4528', 'Switzerland');
INSERT INTO address (id, street, number, city, zip, country) VALUES (2, 'Bahnhofstrasse', '6', 'Windisch', '5210', 'Switzerland');


INSERT INTO user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (1, 'dani_01', 'Daniel', 'Gergely', 'daniel.gergely@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '1', 'male', 'USER');
INSERT INTO user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (2, 'est_01', 'Esther', 'Collins', 'esther.collins@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '2', 'female', 'USER');
