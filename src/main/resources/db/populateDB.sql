DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, id, dateTime, description, calories) VALUES
  (100000, 1, '2015-05-30 09:00:00' , 'Breakfast', 500),
  (100000, 2, '2015-05-30 14:00:00' , 'Dinner', 1000),
  (100000, 3, '2015-05-30 19:00:00' , 'Lunch', 510),
  (100000, 4, '2015-05-31 09:00:00' , 'Breakfast', 500),
  (100000, 5, '2015-05-31 14:00:00' , 'Dinner', 1000),
  (100000, 6, '2015-05-31 19:00:00' , 'Lunch', 500),
  (100001, 7, '2015-05-31 14:00:00' , 'AdminDinner', 1000),
  (100001, 8, '2015-05-31 19:00:00' , 'AdminLunch', 500);