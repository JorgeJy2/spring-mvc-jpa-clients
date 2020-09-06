
INSERT INTO clients (id, name, first_name, email, create_at, picture ) VALUES (1, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture ) VALUES (2, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture ) VALUES (3, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture ) VALUES (4, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (5, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (6, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (7, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (8, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (9, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (10, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (11, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (12, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (13, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (14, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (15, 'Jorge', 'Jacobo', 'jorge@gmail.com', '2020-09-30', '');
INSERT INTO clients (id, name, first_name, email, create_at, picture) VALUES (16, 'Amanda', 'Franco', 'amanda@gmail.com', '2020-10-30', '');

INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'Monitor dell', 5500);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'Mouse', 500);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'SmartPhone', 7500);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'IPad', 1000);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'IPhone', 7500);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'MacBook', 15500);
INSERT INTO products (create_at, name, price) VALUES('2020-10-30', 'Headphones', 2500);

INSERT INTO bills (create_at, description, observation, client_id) VALUES(NOW(), 'Buy products', 'good client', 1);
INSERT INTO bills (create_at, description, observation, client_id) VALUES(NOW(), 'Buy products', 'good client', 2);

INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 1, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(2, 2, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 3, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(4, 4, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 1, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 2, 1);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 5, 1);

INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 1, 2);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 2, 2);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(2, 3, 2);
INSERT INTO items_bill (quantity, product_id, bill_id) VALUES(1, 4, 2);



