CREATE TABLE social_user
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR        NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    password  VARCHAR        NOT NULL
);