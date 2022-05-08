INSERT INTO address (id, street, number, city, zip, country) VALUES (999, 'Luterbachstrasse', '29', 'Zuchwil', '4528', 'Switzerland') ON CONFLICT DO NOTHING;
INSERT INTO address (id, street, number, city, zip, country) VALUES (998, 'Bahnhofstrasse', '6', 'Windisch', '5210', 'Switzerland') ON CONFLICT DO NOTHING;


INSERT INTO shipster_user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (999, 'dani_01', 'Daniel', 'Gergely', 'daniel.gergely@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '999', 'male', 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO shipster_user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (998, 'est_01', 'Esther', 'Collins', 'esther.collins@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '998', 'female', 'USER') ON CONFLICT DO NOTHING;
