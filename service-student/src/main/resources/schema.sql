create table if not exists service_student.students
(
    id           bigserial primary key not null,
    student_name text                  not null,
    username     text                  not null,
    password     text                  not null,
    email        text                  not null
);

insert into service_student.students (student_name, username, password, email)
values ('Lannis', 'lannis', '$2a$10$xzYpKFy48SanpwyfxPp/p.lqQDHaeCBgZjm/xgpc74CGzIBUftffy', 'nphoangtu@gmail.com');