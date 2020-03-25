-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner2','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner3','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner4','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner4','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner5','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner5','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner6','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner6','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner7','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner7','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner8','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner8','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner9','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner9','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner10','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner10','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('vet2','v3t',TRUE);
INSERT INTO authorities VALUES ('vet2','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('vet3','v3t',TRUE);
INSERT INTO authorities VALUES ('vet3','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('vet4','v3t',TRUE);
INSERT INTO authorities VALUES ('vet4','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('vet5','v3t',TRUE);
INSERT INTO authorities VALUES ('vet5','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('vet6','v3t',TRUE);
INSERT INTO authorities VALUES ('vet6','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('adiestrador1','adiestrador',TRUE);
INSERT INTO authorities VALUES ('adiestrador1','adiestrador');

INSERT INTO users(username,password,enabled) VALUES ('adiestrador2','adiestrador',TRUE);
INSERT INTO authorities VALUES ('adiestrador2','adiestrador');

INSERT INTO vets VALUES (1, 'James', 'Carter', 200, 1,'vet1');
INSERT INTO vets VALUES (2, 'Helen', 'Leary', 300, 2,'vet2');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas', 300, 3,'vet3');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega', 300, 4,'vet4');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens', 200, 5,'vet5');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins', 400, 5,'vet6');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin',1200, '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis',1200, '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner2');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez',1200, '2693 Commerce St.', 'McFarland', '6085558763', 'owner3');
INSERT INTO owners VALUES (4, 'Harold', 'Davis',1200, '563 Friendly St.', 'Windsor', '6085553198', 'owner4');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish',1200, '2387 S. Fair Way', 'Madison', '6085552765', 'owner5');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman',1200, '105 N. Lake St.', 'Monona', '6085552654', 'owner6');
INSERT INTO owners VALUES (7, 'Jeff', 'Black',1200, '1450 Oak Blvd.', 'Monona', '6085555387', 'owner7');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito',1200, '345 Maple St.', 'Madison', '6085557683', 'owner8');
INSERT INTO owners VALUES (9, 'David', 'Schroeder',1200, '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner9');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban',1200, '2335 Independence La.', 'Waunakee', '6085555487', 'owner10');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO adiestrador VALUES (1, 'Alberto', 'Carter',1200, 5,695857412,1,'adiestrador1');
INSERT INTO adiestrador VALUES (2, 'Manuel', 'Carter',1200, 2,954663625,2, 'adiestrador2');

INSERT INTO causa VALUES (1, 120, '2020-05-03', '2020-01-01', 1200, 'Mi mascota', true);
INSERT INTO causa VALUES (3, 150, '2020-05-11', '2020-01-11', 1200, 'Mi super mascota', true);

INSERT INTO donacion VALUES (1, 120, 1, 'vet1');
INSERT INTO donacion VALUES (2, 220, 3, 'owner1');

INSERT INTO tipos_Operaciones VALUES (1, 'Cirugia basica');
INSERT INTO tipos_Operaciones VALUES (2, 'Cirugia dental');
INSERT INTO tipos_Operaciones VALUES (3, 'Cirugia de emergencia');
INSERT INTO tipos_Operaciones VALUES (4, 'Cirugia visual');

INSERT INTO tipos_Adiestramiento VALUES (1, 'Adiestramiento deportivo');
INSERT INTO tipos_Adiestramiento VALUES (2, 'Adiestramiento en obediencia basica');
INSERT INTO tipos_Adiestramiento VALUES (3, 'Adiestramiento asistencia y terapia');
INSERT INTO tipos_Adiestramiento VALUES (4, 'Adiestramiento para funciones especiales');

INSERT INTO citas_Operaciones VALUES (1,30,'2020-12-29', '17:00',false,50.0,3,3,2,2);
INSERT INTO citas_Operaciones VALUES (2,30,'2020-12-9', '17:00',false,50.0,3,2,4,1);
INSERT INTO citas_Operaciones VALUES (3,30,'2020-12-11', '17:00',false,50.0,3,1,2,3);
INSERT INTO citas_Operaciones VALUES (4,30,'2020-12-12', '17:00',false,50.0,3,4,1,2);

INSERT INTO citas_Adiestramiento VALUES (1,30,'2020-12-12', '17:00',false,50.0,1,1,1,2);
INSERT INTO citas_Adiestramiento VALUES (2,30,'2020-12-12', '17:00',false,50.0,2,2,2,1);
INSERT INTO citas_Adiestramiento VALUES (3,30,'2020-12-12', '17:00',false,50.0,1,1,1,2);
INSERT INTO citas_Adiestramiento VALUES (4,30,'2020-12-12', '17:00',false,50.0,2,2,2,3);

