-- таблица directions (с направлениями соревнований):
CREATE TABLE IF NOT EXISTS directions(
    direction_id integer GENERATED BY DEFAULT AS IDENTITY,
    name varchar(20) NOT NULL,
    CONSTRAINT pk_direction PRIMARY KEY (direction_id),
    CONSTRAINT uq_direction_name UNIQUE (name)
);

-- таблица users (с пользователями):
CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50),
    patronymic varchar(50),
    birthday date,
    sex varchar(6),
    email varchar(254) NOT NULL,
    phone varchar(20),
    social_link varchar(200),
    country varchar(50),
    region varchar(50),
    city varchar(50),
    portfolio_link varchar(200),
    about_user varchar(5000),
    password varchar(50) NOT NULL,
    registration_date timestamp NOT NULL,
    direction_id integer,
    participation boolean,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT fk_user_to_directions FOREIGN KEY (direction_id) REFERENCES directions(direction_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- таблица roles (с ролями пользователей):
CREATE TABLE IF NOT EXISTS roles(
    role_id integer GENERATED BY DEFAULT AS IDENTITY,
    name varchar(20) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id),
    CONSTRAINT uq_role_name UNIQUE (name)
);

-- таблица user_roles (связывает users и roles):
CREATE TABLE IF NOT EXISTS user_roles(
    user_id BIGINT NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT uq_user_role UNIQUE (user_id, role_id),
    CONSTRAINT fk_user_roles_to_users FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_user_roles_to_roles FOREIGN KEY (role_id) REFERENCES roles(role_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);