CREATE TABLE message(
        id int NOT NULL PRIMARY KEY,
        filename CHAR (255),
        tag CHAR (255),
        text CHAR (255),
        user_id int
);

CREATE TABLE usr(
        id int NOT NULL PRIMARY KEY,
        active BOOLEAN NOT NULL,
        password char (255),
        username char (255)
);

CREATE TABLE user_role(
        user_id int NOT NULL,
        roles char (255)
);

INSERT INTO usr VALUES (1, true, 'u', 'u');
INSERT INTO usr VALUES (2, true, 'usr', 'usr');
INSERT INTO usr VALUES (3, true, 'a', 'a');
INSERT INTO usr VALUES (4, true, 'admin', 'admin');
INSERT INTO usr VALUES (5, true, 'artem', 'artem');

INSERT INTO user_role VALUES (1, 'USER');
INSERT INTO user_role VALUES (2, 'USER');
INSERT INTO user_role VALUES (3, 'ADMIN');
INSERT INTO user_role VALUES (4, 'ADMIN');
INSERT INTO user_role VALUES (5, 'USER');
INSERT INTO user_role VALUES (5, 'ADMIN');

commit;