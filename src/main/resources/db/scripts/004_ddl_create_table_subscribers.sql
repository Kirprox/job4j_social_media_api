CREATE TABLE subscribers
(
    id SERIAL PRIMARY KEY,
    subscriber_id INT NOT NULL REFERENCES social_user(id),
    subcriber_to_id INT NOT NULL REFERENCES social_user(id),
    created_at TIMESTAMP,
    UNIQUE(subscriber_id, subscriber_to_id)
);