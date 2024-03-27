CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    `name` TEXT NOT NULL,
    code VARCHAR(10) NOT NULL,
    instructor TEXT NOT NULL,
    `description` TEXT NOT NULL,
    `status` TEXT,
    creation_date TIMESTAMP,
    inactivation_date TIMESTAMP 
);
