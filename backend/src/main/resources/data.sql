INSERT INTO USERS (email, enabled, first_name, last_name, last_password_reset_date, password, username)
VALUES ('todorovic.1milan@gmail.com', true, 'Milan', 'Todorovic', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y',
        'mtodorovic');
INSERT INTO USERS (email, enabled, first_name, last_name, last_password_reset_date, password, username)
VALUES ('johndoe@test.com', true, 'John', 'Doe', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y',
        'johndoe');
INSERT INTO USERS (email, enabled, first_name, last_name, last_password_reset_date, password, username)
VALUES ('janedoe@test.com', true, 'Jane', 'Doe', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y',
        'janedoe');

INSERT INTO ROLE (name)
VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name)
VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO ROLE (name)
VALUES ('ROLE_USER');

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 3);

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 3);

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 3);

INSERT INTO COMPANY (city, company_description, country, email, name, phone_number)
VALUES ('Novi Sad', 'Some desc', 'Serbia', 'ftn@test.com', 'FTN', '+3811111111');