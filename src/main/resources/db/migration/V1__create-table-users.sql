CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    `name` TEXT NOT NULL,
    username VARCHAR(20) NOT NULL,
    email TEXT NOT NULL,
    `password` TEXT NOT NULL,
    `role` TEXT NOT NULL,
    creation_date TIMESTAMP 
);