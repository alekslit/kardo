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