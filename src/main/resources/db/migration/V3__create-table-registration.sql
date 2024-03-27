CREATE TABLE registration (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    registration_date TIMESTAMP
);