create table persons (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000)
);

create table employees (
    id serial primary key not null,
    first_name varchar(200),
    last_name varchar(200),
	inn varchar(50),
	hiring date default now()
);

create table employees_persons (
    employee_id int,
	person_id int
);

insert into persons (login, password) values ('parsentev', '123');
insert into persons (login, password) values ('ban', '123');
insert into persons (login, password) values ('ivan', '123');