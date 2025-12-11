CREATE TABLE social_user
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(50) UNIQUE NOT NULL,
    email     VARCHAR(20) UNIQUE NOT NULL,
    password  VARCHAR(120)       NOT NULL
);