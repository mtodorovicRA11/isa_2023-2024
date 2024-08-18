INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password, penalty_points, phone_number, username)
VALUES ('todorovic.1milan@gmail.com', true, 'Milan', 1111111111111, 'student', 'Todorovic', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y', 0,12341555,'mtodorovic');
INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password, penalty_points, phone_number, username)
VALUES ('johndoe@test.com', true, 'John', 1111111111111, 'student', 'Doe', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y',0,12341555,'johndoe');
INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password, penalty_points, phone_number, username)
VALUES ('janedoe@test.com', true, 'Jane', 1111111111111, 'student', 'Doe', now(), '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y',0,12341555,'janedoe');

INSERT INTO role (name)
VALUES ('ROLE_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_USER');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role (user_id, role_id)
VALUES (1, 2);
INSERT INTO user_role (user_id, role_id)
VALUES (1, 3);

INSERT INTO user_role (user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role (user_id, role_id)
VALUES (2, 3);

INSERT INTO user_role (user_id, role_id)
VALUES (3, 3);

INSERT INTO company (city, company_description, country, email, name, phone_number)
VALUES ('Novi Sad', 'Some desc', 'Serbia', 'ftn@test.com', 'FTN', '+3811111111');