-- заполним таблицу roles_import:
-- Идентификаторы ролей (1 - USER, 2 - ADMIN, 3 - MODERATOR, 4 - JUDGE)
INSERT INTO roles_import (name)
VALUES('USER');
INSERT INTO roles_import (name)
VALUES('ADMIN');
INSERT INTO roles_import (name)
VALUES('MODERATOR');
INSERT INTO roles_import (name)
VALUES('JUDGE');

-- заполним таблицу roles:
MERGE INTO roles r
USING roles_import ri
ON (r.name = ri.name)
WHEN NOT MATCHED THEN
INSERT (name)
VALUES (ri.name);

-- удаляем таблицу для импорта:
DROP TABLE roles_import;