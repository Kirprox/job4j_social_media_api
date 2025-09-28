CREATE TABLE post
(
    id   SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    text VARCHAR NOT NULL,
    file_id INT NOT NULL REFERENCES files(id),
    user_id INT NOT NULL REFERENCES social_user(id),
    created TIMESTAMP NOT NULL
);