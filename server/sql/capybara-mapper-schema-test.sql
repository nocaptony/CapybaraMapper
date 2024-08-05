drop database if exists capybara_mapper_test;
create database capybara_mapper_test;
use capybara_mapper_test;

-- create tables and relationships
create table `user` (
    user_id int primary key auto_increment,
    date_of_birth date not null,
    name varchar(250) not null,
    email varchar(250) not null,
    phone_number varchar(50) not null
);

create table location (
    location_id int primary key auto_increment,
    user_id int not null,
    location point not null,
	constraint fk_location_user_id
    foreign key (user_id)
    references user(user_id)
);



create table pet (
    pet_id int primary key auto_increment,
    pet_type_id int not null,
    location_id int not null,
    name varchar(1000) not null,
	description varchar(1000) not null,
    photo_link varchar(1000) null,
    constraint fk_pet_location_id
    foreign key (location_id)
    references location(location_id)
);

create table pet_type (
    pet_type_id int primary key auto_increment,
    name varchar(250) not null
);

create table adoption (

    adoption_id int primary key auto_increment,
    pet_id int not null,
    user_id int not null,
    street_address varchar(50) not null,
    city varchar(50) not null,
    state varchar(50) not null,
    zip_code varchar(50) not null,
    email varchar(250) not null,
    phone_number varchar(250) not null,
    request_description varchar(1000) null,

    constraint fk_adoption_pet_id
    foreign key (pet_id)
    references pet(pet_id),
    constraint fk_adoption_user_id
    foreign key (user_id)
    references user(user_id)
);

-- data

delimiter //
create procedure set_known_good_state()
begin

	delete from adoption;
	alter table adoption auto_increment = 1;
	delete from pet;
    alter table pet auto_increment = 1;
	delete from pet_type;
    alter table pet_type auto_increment = 1;
	delete from location;
    alter table location auto_increment = 1;
	delete from `user`;
    alter table `user` auto_increment = 1;


	insert into user(user_id, date_of_birth, name, email, phone_number) values
		(1, '2000-01-01', 'Sherman Animal Shelter', 'info@shermananimals.org', '313-839-1283'),
		(2, '2008-01-01', 'Mansfield County Animal Shelter', 'info@mansfieldcountyAS.com', '804-512-2154'),
		(3, '1997-07-04', 'Ryann Jacobs', 'tommie1984@gmail.com', '973-969-2480'),
		(4, '1971-06-11', 'Catherine R Chapman', 'estefania_he@yahoo.com', '717-939-1241');


	insert into location(location_id, user_id, location) values
		(1, 1, POINT(39.887264, -74.741545)), -- Red Lion Diner
		(2, 2, POINT(39.893211, -74.741414)), -- Red Star Pizza
		(3, 1, POINT(39.853281, -74.522546)); -- Woodland, NJ

	
	insert into pet_type (pet_type_id, name)
		values
	(1, 'Capybara'),
	(2, 'Dog'),
	(3, 'Cat'),
	(4, 'Zebra'),
	(5, 'Koala'),
	(6, 'Pangolin'),
	(7, 'Monkey'),
	(8, 'Racoon'),
	(9, 'Red Panda'),
	(10, 'Giraffe');
    
    insert into pet(pet_id, pet_type_id, location_id, name, description, photo_link) values
	(1, 1, 1, "Reginald", "fun capy", "https://images.unsplash.com/photo-1633123784883-9cc9ba6d8c9e?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
	(2, 1, 2, "Turkey", "cute capy" "https://images.unsplash.com/photo-1557431177-36141475c676?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
	(3, 2, 3, "Oakly", "interesting capy","https://images.unsplash.com/photo-1714622343930-42e889c677d9?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

	insert into adoption(adoption_id, pet_id, user_id, street_address, city, state, zip_code, email, phone_number, request_description) values
		(1, 1, 3, '1234 Elm St', 'Newark', 'NJ', '07102', 'tommie1984@gmail.com', '973-969-2480', 'Looking for a gentle and calm pet for companionship.'),
		(2, 2, 3, '5678 Maple Dr', 'Newark', 'NJ', '07103', 'tommie1984@gmail.com', '973-969-2480', 'Seeking a playful companion for backyard fun.'),
		(3, 3, 3, '91011 Oak Ln', 'Clifton', 'NJ', '07011', 'tommie1984@gmail.com', '973-969-2480', 'Interested in adopting a young pet who loves walks.'),
		(4, 1, 4, '1213 Pine Ave', 'Harrisburg', 'PA', '17101', 'estefania_he@yahoo.com', '717-939-1241', 'Looking for a friendly pet to bring joy to our home.'),
		(5, 3, 4, '1415 Spruce St', 'Harrisburg', 'PA', '17102', 'estefania_he@yahoo.com', '717-939-1241', 'I would love a pet that can handle lots of love and attention.');

    
    end //
-- 4. Change the statement terminator back to the original.
delimiter ;

