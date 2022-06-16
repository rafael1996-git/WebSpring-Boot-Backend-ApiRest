/* Populate tabla clientes */
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06','');
/* Populate tabla clientes */
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05','');
INSERT INTO clientes (nombre, apellido, email, create_at,foto) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06','');

/* Creamos algunos usuarios con sus roles */
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',true, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);