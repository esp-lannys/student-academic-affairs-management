drop table if exists service_retention.retention;
create table service_retention.retentions (
    id serial NOT NULL PRIMARY KEY,
    student_id bigint not null,
    note text DEFAULT NULL,
    student_email varchar(255) not null,
    student_name varchar(255) not null,
    reason varchar(255) not null
);

insert into service_retention.retentions (note, reason, student_id, student_name, student_email) values ('Temporary Leave', 'Em buon lam',2 ,'Nguyen Phan Hoang Tu', 'nphoangtu@gmail.com');

insert into service_retention.retentions (note, reason, student_id, student_name, student_email) values ('Permanently Leave', 'Cuoc song nay that kho khan', 3, 'Nguyen  Van A', '8aothuat@gmail.com');

create sequence if not exists service_retention.seq_retention start 100 increment 1;