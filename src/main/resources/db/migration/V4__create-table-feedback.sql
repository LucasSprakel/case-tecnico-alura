CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    rating TINYINT NOT NULL,
    feedback TEXT NOT NULL
);