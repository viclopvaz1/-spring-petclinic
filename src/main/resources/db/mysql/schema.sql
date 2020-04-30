   create table adiestrador (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name varchar(255),
        last_name varchar(255),
        monedero integer check (monedero>=0),
        estrellas integer check (estrellas<=5 AND estrellas>=0),
        telefono integer,
        tipo_animal_id integer,
        username varchar(255)
    );
    
    
    create table authorities (
       username varchar(255) not null,
        authority varchar(255),
        primary key (username)
    );
    
    create table causa (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        dinero_recaudado integer not null,
        fecha_fin date not null,
        fecha_inicio date not null,
        objetivo integer not null,
        ong varchar(255),
        valido boolean
    );
    
    create table citas_adiestramiento (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        duracion integer not null check (duracion>=1),
        fecha date not null,
        hora time not null,
        pagado boolean,
        precio double not null check (precio>=1),
        pet_id integer,
        adiestrador_id integer,
        owner_id integer,
        tipo_adiestramiento_id integer
    ); 
    
    create table citas_operaciones (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        duracion integer not null check (duracion>=1),
        fecha date not null,
        hora time not null,
        pagado boolean,
        precio double not null check (precio>=1),
        cantidad_personal double not null check (cantidad_personal>=1),
        pet_id integer,
        tipo_operacion_id integer,
        vet_id integer
    );
    
    create table donacion (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        cantidad integer not null check (cantidad>=1),
        causa_id integer,
        username varchar(255)
    );
    
    create table owners (
      id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name varchar(255),
        last_name varchar(255),
        monedero integer check (monedero>=0),
        address varchar(255),
        city varchar(255),
        telephone varchar(255),
        username varchar(255)
    );
    
    create table pets (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50),
        birth_date date,
        owner_id integer,
        type_id integer
    );
    
    create table specialties (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50)
    );
    
    create table tipos_adiestramiento (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50)
    );
    
    create table tipos_operaciones (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50)
    );
    
    create table types (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50)
    );
    
    create table users (
       username varchar(255) not null,
        enabled boolean not null,
        password varchar(255),
        primary key (username)
    );
    
    create table vet_specialties (
       vet_id integer not null,
        specialty_id integer not null,
        primary key (vet_id, specialty_id)
    );
    
    create table vets (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name varchar(255),
        last_name varchar(255),
        monedero integer check (monedero>=0),
        estrellas integer not null check (estrellas<=5 AND estrellas>=0),
        username varchar(255)
    );
    
    create table visits (
       id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        visit_date date,
        description varchar(255),
        pet_id integer
    );