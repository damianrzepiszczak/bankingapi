create table clients (
    id int AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    login varchar(250) unique not null,
    password varchar(250) not null,
    date_of_birth date not null,
    jwt varchar(250),
    created_at date DEFAULT CURRENT_TIMESTAMP
);

create table accounts (
    id int AUTO_INCREMENT PRIMARY KEY,
    account_number varchar(250) not null,
    balance int not null,
    client_id int not null,
    opening_date date DEFAULT CURRENT_TIMESTAMP,
    foreign key (client_id) references clients(id)
);