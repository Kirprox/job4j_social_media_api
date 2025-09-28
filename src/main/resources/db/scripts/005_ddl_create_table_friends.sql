CREATE TABLE friends
(
    id SERIAL PRIMARY KEY,
    user1_id INT NOT NULL REFERENCES social_user(id),
    user2_id INT NOT NULL REFERENCES social_user(id),
    created_at TIMESTAMP NOT NULL,
    UNIQUE(user1_id, user2_id)
);