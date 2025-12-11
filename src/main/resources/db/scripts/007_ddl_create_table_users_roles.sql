CREATE TABLE users_roles
(
    role_id INT REFERENCES roles(id),
    user_id INT REFERENCES social_user(id)
);