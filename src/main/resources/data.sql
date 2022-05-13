INSERT INTO address (id, street, number, city, zip, country) VALUES (999, 'Luterbachstrasse', '29', 'Zuchwil', '4528', 'Switzerland') ON CONFLICT DO NOTHING;
INSERT INTO address (id, street, number, city, zip, country) VALUES (998, 'Bahnhofstrasse', '6', 'Windisch', '5210', 'Switzerland') ON CONFLICT DO NOTHING;


INSERT INTO shipster_user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (999, 'dani_01', 'Daniel', 'Gergely', 'daniel.gergely@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '999', 'male', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO shipster_user (user_id, user_name, first_name, last_name, email, password, address_id, gender, roles) VALUES (998, 'est_01', 'Esther', 'Collins', 'esther.collins@students.fhnw.ch', '$2a$10$.oTsOd/w3Lx4xdr3LGC6qe6aYOSZpKIbDi4mq5AgmjT2j29KhT5Bi', '998', 'female', 'USER') ON CONFLICT DO NOTHING;


INSERT INTO article (id, name, description, image_url, price, pallet_space, max_stack) VALUES (1, 'Article P1', 'Our most first of our Ps', 'https://media.istockphoto.com/photos/alphabet-antique-block-letters-isolated-on-white-letter-a-picture-id471440765', 175.95, 1.2,25) ON CONFLICT DO NOTHING;
INSERT INTO article (id, name, description, image_url, price, pallet_space, max_stack) VALUES (2, 'Article P2', 'For those, who need more that just P1', 'https://live.staticflickr.com/7236/6981941244_cbe55d6aa4.jpg', 215.95, 2, 10)ON CONFLICT DO NOTHING;
INSERT INTO article (id, name, description, image_url, price, pallet_space, max_stack) VALUES (3, 'Article P3', 'Its basically P3, but more expensive', 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.b-P01RZ_J-VhSj5OLqoV5QAAAA%26pid%3DApi&f=1', 225.95, 2.5,15)ON CONFLICT DO NOTHING;
INSERT INTO article (id, name, description, image_url, price, pallet_space, max_stack) VALUES (4, 'Article P4', 'A top-notch P', 'https://www.sydneyprops.com.au/wp-content/uploads/2017/09/d-letter-marquee-letter-sydney-props-hire.jpg', 315.95, 0.8, 100)ON CONFLICT DO NOTHING;
INSERT INTO article (id, name, description, image_url, price, pallet_space, max_stack) VALUES (5, 'Article P5', 'We have more than 4 articles in our DB', 'https://www.publicdomainpictures.net/pictures/180000/velka/letter-e-floral-initial.jpg', 95.95, .4, 7)ON CONFLICT DO NOTHING;

INSERT INTO shipster_order (id, user_id, order_status) VALUES (1, 999, 'BASKET') ON CONFLICT DO NOTHING;
INSERT INTO shipster_order (id, user_id, order_status) VALUES (2, 998, 'ORDERED') ON CONFLICT DO NOTHING;
INSERT INTO shipster_order (id, user_id, order_status) VALUES (3, 999, 'ORDERED') ON CONFLICT DO NOTHING;
INSERT INTO shipster_order (id, user_id, order_status) VALUES (4, 999, 'ORDERED') ON CONFLICT DO NOTHING;
INSERT INTO shipster_order (id, user_id, order_status) VALUES (5, 999, 'ORDERED') ON CONFLICT DO NOTHING;

/*                                                          //  id art ord q  // */
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (1, 1, 1, 3) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (2, 3, 1, 10) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (3, 2, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (5, 4, 2, 2) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (6, 5, 3, 12) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (7, 1, 4, 3) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (8, 1, 5, 8) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (9, 2, 5, 1) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (10, 3, 5, 1) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (12, 4, 5, 5) ON CONFLICT DO NOTHING;
INSERT INTO order_item (id, article_id, order_id, quantity) VALUES (13, 5, 5, 28) ON CONFLICT DO NOTHING;
