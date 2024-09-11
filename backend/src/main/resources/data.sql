INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password,
                   penalty_points, phone_number, username)
VALUES ('todorovic.1milan@gmail.com', true, 'Milan', 1111111111111, 'student', 'Todorovic', now(),
        '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y', 0, 12341555, 'mtodorovic');
INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password,
                   penalty_points, phone_number, username)
VALUES ('johndoe@test.com', true, 'John', 1111111111111, 'student', 'Doe', now(),
        '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y', 0, 12341555, 'johndoe');
INSERT INTO users (email, enabled, first_name, jmbg, job_title, last_name, last_password_reset_date, password,
                   penalty_points, phone_number, username)
VALUES ('janedoe@test.com', true, 'Jane', 1111111111111, 'student', 'Doe', now(),
        '$2a$10$EcAOqEc7BPW6UccFJ37OMuCOszleHimUpmVGbMvYDAk7IH7xPQb8y', 0, 12341555, 'janedoe');

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
VALUES ('Novi Sad', 'Fakultet Tehnickih nauka', 'Serbia', 'ftn@test.com', 'FTN', '+3811111111'),
       ('Beograd', 'ETF DESC', 'Serbia', 'etf@etf.com', 'ETF', '+3811111112');

INSERT INTO equipment (name, amount, company_id)
VALUES ('Stethoscope', 50, 1),
       ('Sphygmomanometer', 75, 1),
       ('Thermometer', 100, 1),
       ('Surgical Scissors', 30, 1),
       ('Defibrillator', 20, 1),
       ('Surgical Mask', 80, 2),
       ('Syringe', 150, 2),
       ('Hospital Bed', 15, 2),
       ('Wheelchair', 10, 2),
       ('Oxygen Cylinder', 5, 2);

INSERT INTO timeslot (start_time, end_time, is_available, equipment_id)
VALUES ('2024-09-12 09:00:00', '2024-09-12 10:00:00', true, 1),
       ('2024-09-12 10:00:00', '2024-09-12 11:00:00', true, 1),
       ('2024-09-12 11:00:00', '2024-09-12 12:00:00', true, 1),
       ('2024-09-12 12:00:00', '2024-09-12 13:00:00', true, 2),
       ('2024-09-12 13:00:00', '2024-09-12 14:00:00', true, 2),
       ('2024-09-12 14:00:00', '2024-09-12 15:00:00', true, 2),
       ('2024-09-12 09:00:00', '2024-09-12 10:00:00', true, 3),
       ('2024-09-12 10:00:00', '2024-09-12 11:00:00', true, 3),
       ('2024-09-12 11:00:00', '2024-09-12 12:00:00', true, 3),
       ('2024-09-13 09:00:00', '2024-09-13 10:00:00', true, 1),
       ('2024-09-13 10:00:00', '2024-09-13 11:00:00', true, 1),
       ('2024-09-13 11:00:00', '2024-09-13 12:00:00', true, 2),
       ('2024-09-13 12:00:00', '2024-09-13 13:00:00', true, 2),
       ('2024-09-13 13:00:00', '2024-09-13 14:00:00', true, 3),
       ('2024-09-13 14:00:00', '2024-09-13 15:00:00', true, 3);