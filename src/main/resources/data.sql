-- заполним таблицу roles:
-- Идентификаторы ролей (1 - USER, 2 - ADMIN, 3 - MODERATOR, 4 - JUDGE)
INSERT INTO roles (name)
VALUES ('USER'), ('ADMIN'), ('MODERATOR'), ('JUDGE')
ON CONFLICT (name) DO NOTHING;

-- заполним таблицу directions:
INSERT INTO directions (name)
VALUES('BMX'), ('KICK_SCOOTERING'), ('HIP_HOP'), ('PARKOUR'),
      ('FREE_RUNNING'), ('TRICKING'), ('BREAKING'),
      ('WORKOUT'), ('GRAFFITI'), ('SKATEBOARDING'), ('DJING')
ON CONFLICT (name) DO NOTHING;

/*
-- тестовые Admin пользователи:
-- id 1:
INSERT INTO users (first_name, email, password, registration_date)
VALUES ('Елена', 'elena@mail.ru', '12345678', '2024-08-11T15:28:03.136329500')
ON CONFLICT (email) DO NOTHING;
INSERT INTO user_roles (user_id, role_id)
VALUES ('1', '1'), ('1', '2')
ON CONFLICT (user_id, role_id) DO NOTHING;
-- id 2:
INSERT INTO users (first_name, email, password, registration_date)
VALUES ('Роман', 'roman@mail.ru', '12345678', '2024-08-11T15:29:03.136329500')
ON CONFLICT (email) DO NOTHING;
INSERT INTO user_roles (user_id, role_id)
VALUES ('2', '1'), ('2', '2')
ON CONFLICT (user_id, role_id) DO NOTHING;*/